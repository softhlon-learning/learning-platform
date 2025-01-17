import {Component, HostListener, OnInit} from '@angular/core';
import {Course} from "../home/course";
import {PlatformService} from '../service/platform.service';
import {ActivatedRoute, Router} from "@angular/router";
import {CourseContent} from "../course-content/course-content";
import {NavigationItems} from "../course-navigation/navigation-items";
import {CookieService} from "ngx-cookie-service";
import {Lecture} from "../course-content/lecture";

@Component({
    selector: 'course-progress',
    templateUrl: './course-progress.component.html',
    styleUrls: ['./course-progress.component.css']
})
export class CourseProgressComponent implements OnInit {
    course: Course = {};
    courseContent?: CourseContent;
    navigationLectures = new NavigationItems();

    constructor(
        private coursesService: PlatformService,
        private cookieService: CookieService,
        private route: ActivatedRoute,
        private router: Router) {
    }

    @HostListener('window:keydown', ['$event'])
    keyboardInput(event: any) {
        // event.preventDefault();
        event.stopPropagation()

        if (event.code == 'ArrowDown' || event.code == 'ArrowRight') {
            this.next();
        }

        if (event.code == 'ArrowUp' || event.code == 'ArrowLeft') {
            this.previous()
        }

        if (event.code == 'KeyM') {
            this.switchLectureViewedFlag();
        }
    }

    ngOnInit() {
        if (this.cookieService.get('Authenticated') !== 'true') {
            this.router.navigate(['/sign-in'])
                .then(() => {
                    window.location.reload();
                });
            ;
        }
        this.refreshPageState();
        this.getCourse();
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
                this.findCurrentItem(courseContent);
            })
    }

    setLecture(selectedLecture: Lecture): void {
        let tempNavigationLectures: NavigationItems = new NavigationItems();
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

    next(): NavigationItems {
        if (this.navigationLectures.nextLecture != null) {
            this.setLecture(this.navigationLectures.nextLecture);
        }
        this.scrollToElement(this.navigationLectures.currentLecture.id);
        return this.navigationLectures;
    }

    previous(): NavigationItems {
        if (this.navigationLectures.previousLecture != null) {
            this.setLecture(this.navigationLectures.previousLecture);
        }
        this.scrollToElement(this.navigationLectures.currentLecture.id);
        return this.navigationLectures;
    }

    getClass(lecture: Lecture): string {
        return lecture.selected ? 'selected' : '';
    }

    scrollToElement(id: string): void {
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
        this.next();
        this.updateCourse();
    }

    switchLectureViewedFlag(): void {
        let lecture: Lecture | null = this.getCurrentLecture();
        if (lecture != null) {
            if (lecture.processed) {
                lecture.processed = false
            } else {
                lecture.processed = true;
            }
        }
        this.next();
        this.updateCourse();
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

    updateCourse(): void {
        let courseContentB64 = btoa(JSON.stringify(this.courseContent));
        this.coursesService.updateCourse(this.course.id ?? '', courseContentB64).subscribe();
    }

    refreshPageState() {
        this.coursesService.refreshCourses().subscribe(() => this.getCourse());
    }

    typeVerb(lecture: Lecture): string {
        if (lecture.type === "Video") {
            return "Watch";
        }
        if (lecture.type === "PDF") {
            return "Read";
        }
        if (lecture.type === "Quiz") {
            return "Interact";
        }
        return lecture.type;
    }
}
