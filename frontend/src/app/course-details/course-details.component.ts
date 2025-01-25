import {Component, HostListener, OnInit} from '@angular/core';
import {Course} from "../home/course";
import {ActivatedRoute, Router} from "@angular/router";
import {CourseContent} from "../model/course-content";
import {NavigationLectures} from "../course-navigation/navigation-lectures";
import {CookieService} from "ngx-cookie-service";
import {Lecture} from "../model/lecture";
import {KeyboardInputCourseDetails} from "./keyboard-input";
import {CoursesService} from '../service/courses.service';

@Component({
    selector: 'course-details',
    templateUrl: './course-details.component.html',
    styleUrls: ['./course-details.component.css']
})
export class CourseDetailsComponent implements OnInit {
    course: Course = {};
    courseContent?: CourseContent;
    navigationLectures = new NavigationLectures();

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
        this.getCourse();
    }

    ngAfterViewInit() {
        // @ts-ignore
        this.findCurrentItem(this.courseContent);
    }

    getCourse(): void {
        const id = this.route.snapshot.paramMap.get('id')!;
        this.coursesService.getCourses()
            .subscribe(courses => {
                for (let i = 0; i < courses.length; i++) {
                    let course = courses[i];
                    if (course.code === id) {
                        this.course = course;
                        break;
                    }
                }
                const courseContent: CourseContent = JSON.parse(atob(<string>this.course.content));
                this.courseContent = courseContent;
            })
    }

    setLecture(selectedLecture: Lecture): void {
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

        this.scrollToElement(selectedLecture.id);
        this.navigationLectures = tempNavigationLectures;
    }

    findCurrentItem(courseContent: CourseContent): void {
        for (let chapter of courseContent.chapters)
            for (let lecture of chapter.lectures)
                if (lecture.selected) {
                    this.setLecture(lecture);
                    return;
                }
        this.setLecture(courseContent.chapters[0].lectures[0]);
    }

    getCurrentLecture(): Lecture | null {
        if (this.courseContent != null) {
            for (let chapter of this.courseContent.chapters)
                for (let lecture of chapter.lectures) {
                    if (lecture.id == this.navigationLectures.currentLecture.id) {
                        this.setLecture(lecture);
                        return lecture;
                    }
                }
        }
        return null;
    }

    next(): NavigationLectures {
        if (this.navigationLectures.nextLecture != null) {
            this.setLecture(this.navigationLectures.nextLecture);
        }
        let lecture: Lecture | null = this.getCurrentLecture();
        this.updateLecture(lecture as Lecture);

        this.scrollToElement(this.navigationLectures.currentLecture.id);
        return this.navigationLectures;
    }

    previous(): NavigationLectures {
        if (this.navigationLectures.previousLecture != null) {
            this.setLecture(this.navigationLectures.previousLecture);
        }

        let lecture: Lecture | null = this.getCurrentLecture();
        this.updateLecture(lecture as Lecture);

        this.scrollToElement(this.navigationLectures.currentLecture.id);
        return this.navigationLectures;
    }

    getClass(lecture: Lecture): string {
        return lecture.selected ? 'selected' : '';
    }

    scrollToElement(id: string): void {
        console.log(id);
        // @ts-ignore
        document.getElementById(id).scrollIntoView({
            behavior: "auto",
            block: "center",
            inline: "center"
        });
    }

    markAsViewed(): void {
        this.markLectureViewedFlag(true);
    }

    markAsNotViewed(): void {
        this.markLectureViewedFlag(false);
    }

    markLectureViewedFlag(viewed: boolean): void {
        let lecture: Lecture | null = this.getCurrentLecture();
        if (lecture != null) {
            lecture.processed = viewed;
        }
        if (viewed) {
            this.next();
        }
        this.updateLecture(lecture as Lecture);
    }

    switchLectureViewedFlag(): void {
        let lecture: Lecture | null = this.getCurrentLecture();
        if (lecture != null) {
            if (lecture.processed) {
                lecture.processed = false
            } else {
                lecture.processed = true;
                this.next();
            }
        }
        this.updateLecture(lecture as Lecture);
    }

    courseProgress(): number {
        let allLecturesCount: number = 0;
        let processedChaptersCount: number = 0;
        if (this.courseContent != null) {
            for (let chapter of this.courseContent.chapters)
                for (let lecture of chapter.lectures) {
                    allLecturesCount++;
                    if (lecture.processed) processedChaptersCount++;
                }
        }
        return Math.round(processedChaptersCount / allLecturesCount * 100);
    }

    updateLecture(lecture: Lecture): void {
        //let courseContentB64 = btoa(JSON.stringify(this.courseContent));
        //this.coursesService.updateCourse(this.course.id ?? '', courseContentB64).subscribe();
        this.coursesService.updateLecture(this.course.id || '', lecture?.id || '', lecture?.processed || false).subscribe();
    }

    refreshPageState() {
        this.coursesService.refreshCourses().subscribe(() => this.getCourse());
    }

    back() {
        this.router.navigate(['/course/' + this.course.code]);
    }

    home() {
        this.router.navigate(['/home']);
    }

    play() {
        let video = document.querySelector('video');
        if (video) {
            video.play();
        }
    }

    typeVerb(lecture: Lecture): string {
        if (lecture.type === "Video") return "Watch";
        if (lecture.type === "Document") return "Read";
        if (lecture.type === "Quiz") return "Interact";
        return lecture.type;
    }
}
