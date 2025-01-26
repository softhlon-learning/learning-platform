// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, HostListener, OnInit} from '@angular/core';
import {Course} from "../home/course";
import {ActivatedRoute, Router} from "@angular/router";
import {CourseContent} from "../model/course-content";
import {NavigationLectures} from "../course-navigation/navigation-lectures";
import {CookieService} from "ngx-cookie-service";
import {Lecture} from "../model/lecture";
import {KeyboardInputCourseDetails} from "./keyboard-input";
import {CoursesService} from '../service/courses/courses.service';

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'course-details',
    templateUrl: './course-details.component.html',
    styleUrls: ['./course-details.component.css']
})
export class CourseDetailsComponent implements OnInit {
    course: Course = {};
    courseContent?: CourseContent;
    navigationLectures = new NavigationLectures();
    currentLecture?: Lecture;

    constructor(
        private keyboardInputDetails: KeyboardInputCourseDetails,
        private coursesService: CoursesService,
        private cookieService: CookieService,
        private route: ActivatedRoute,
        private router: Router) {
    }

    @HostListener('window:keydown', ['$event'])
    keyboardInput(event: any) {
        this.keyboardInputDetails.keyboardInput(this, event);
    }

    ngOnInit() {
        if (this.cookieService.get('Authenticated') !== 'true') {
            this.router.navigate(['/sign-in'])
                .then(() => {
                    window.location.reload();
                });
            ;
        }
        this.fetchCourseAndInitView();
    }

    ngAfterViewInit() {
        this.findAndScrollToCurrentLecture(this.courseContent);
    }

    fetchCourseAndInitView() {
        const id = this.route.snapshot.paramMap.get('id')!;
        this.coursesService.getCourses()
            .subscribe(courses => {
                for (let i = 0; i < courses.length; i++) {
                    let course = courses[i];
                    if (course.code === id) {
                        this.course = course;
                        this.courseContent = JSON.parse(atob(<string>this.course.content));
                        setTimeout(() => this.findAndScrollToCurrentLecture(this.courseContent), 0);
                        break;
                    }
                }
            })
    }

    selectLectureOnly(lecture?: Lecture) {
        this.selectLecture(lecture, false, false);
    }

    selectAndScrollToLecture(lecture?: Lecture) {
        this.selectLecture(lecture, true, false);
    }

    selectScrollToAndPersistLecture(lecture?: Lecture) {
        this.selectLecture(lecture, true, true);
    }

    selectLecture(selectedLecture?: Lecture, scroll: boolean = true, persist: boolean = true): void {
        if (selectedLecture == null) {
            return;
        }

        let tempNavigationLectures: NavigationLectures = new NavigationLectures();
        let currentLecture: Lecture;

        if (this.courseContent) {
            for (let chapter of this.courseContent.chapters)
                for (let lecture of chapter.lectures) {
                    lecture.selected = false;
                    if (tempNavigationLectures.nextLecture != null) {
                        continue;
                    }
                    // @ts-ignore
                    if (currentLecture != null) {
                        tempNavigationLectures.nextLecture = lecture;
                        this.navigationLectures = tempNavigationLectures;
                    } else if (lecture != selectedLecture) {
                        tempNavigationLectures.previousLecture = lecture;
                    } else {
                        currentLecture = lecture;
                        tempNavigationLectures.currentLecture = lecture;
                        tempNavigationLectures.currentLecture.selected = true;
                    }
                }
        }

        if (persist == true) {
            this.persisteLectureState(selectedLecture);
        }

        if (scroll === true) {
            this.scrollToElement(selectedLecture.id);
        }

        this.navigationLectures = tempNavigationLectures;
    }

    findAndScrollToCurrentLecture(courseContent?: CourseContent): void {
        if (courseContent == null) {
            return;
        }
        for (let chapter of courseContent.chapters)
            for (let lecture of chapter.lectures)
                if (lecture.selected) {
                    this.selectAndScrollToLecture(lecture);
                    return;
                }
        this.selectAndScrollToLecture(courseContent.chapters[0].lectures[0]);
    }

    getCurrentLecture(): Lecture {
        if (this.courseContent != null) {
            for (let chapter of this.courseContent.chapters)
                for (let lecture of chapter.lectures) {
                    if (lecture.id == this.navigationLectures.currentLecture.id) {
                        return lecture;
                    }
                }
        }
        // @ts-ignore
        return undefined;
    }

    moveToNextLecture(): NavigationLectures {
        if (this.navigationLectures.nextLecture != null) {
            const nextLecture = this.navigationLectures.nextLecture;
            this.selectScrollToAndPersistLecture(nextLecture);
        } else {
            const currentLecture = this.getCurrentLecture();
            this.selectScrollToAndPersistLecture(currentLecture)
        }

        return this.navigationLectures;
    }

    moveToPreviousLecture(): NavigationLectures {
        if (this.navigationLectures.nextLecture != null) {
            const nextLecture = this.navigationLectures.previousLecture;
            this.selectScrollToAndPersistLecture(nextLecture);
        } else {
            const currentLecture = this.getCurrentLecture();
            this.selectScrollToAndPersistLecture(currentLecture)
        }

        return this.navigationLectures;
    }

    markLectureAsViewed(): void {
        this.markLectureViewedFlag(true);
    }

    markLectureAsNotViewed(): void {
        this.markLectureViewedFlag(false);
    }

    markLectureViewedFlag(viewed: boolean): void {
        let lecture: Lecture | null = this.getCurrentLecture();
        if (lecture != null) {
            lecture.processed = viewed;
        }
        if (viewed) this.moveToNextLecture();
        this.persisteLectureState(lecture as Lecture);
    }

    switchLectureViewedFlag(): void {
        let lecture: Lecture | null = this.getCurrentLecture();
        if (lecture != null) {
            if (lecture.processed) {
                lecture.processed = false
                this.selectScrollToAndPersistLecture(lecture);
            } else {
                lecture.processed = true;
                this.moveToNextLecture();
            }
        }
    }

    calculateCourseProgress(): number {
        let allLecturesCount: number = 0;
        let processedChaptersCount: number = 0;
        if (this.courseContent != null) {
            for (let chapter of this.courseContent.chapters)
                for (let lecture of chapter.lectures) {
                    allLecturesCount++;
                    if (lecture.processed) processedChaptersCount++;
                }
            return Math.round(processedChaptersCount / allLecturesCount * 100);
        } else {
            return 0;
        }
    }

    persisteLectureState(lecture?: Lecture): void {
        console.log(this.courseContent);
        this.course.content = btoa(JSON.stringify(this.courseContent));
        this.coursesService
            .updateLecture(
                this.course.id || '',
                lecture?.id || '',
                lecture?.processed || false)
            .subscribe(() => this.coursesService.updateCourse(this.course));
    }

    moveBack() {
        this.router.navigate(['/course/' + this.course.code]);
    }

    moveToHome() {
        this.router.navigate(['/home']);
    }

    playVideo() {
        let video = document.querySelector('video');
        if (video) {
            video.play();
        }
    }

    getLectureClass(lecture: Lecture): string {
        return lecture.selected ? 'selected' : '';
    }

    scrollToElement(id: string): void {
        // @ts-ignore
        const lectureElement = document.getElementById(id);
        if (lectureElement != null) {
            lectureElement.scrollIntoView({
                behavior: "auto",
                block: "center",
                inline: "center"
            });
        }
    }

    typeVerb(lecture: Lecture): string {
        if (lecture.type === "Video") return "Watch";
        if (lecture.type === "Document") return "Read";
        if (lecture.type === "Quiz") return "Interact";
        return lecture.type;
    }
}
