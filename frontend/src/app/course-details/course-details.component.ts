// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, HostListener, OnInit} from '@angular/core'
import {Course} from "../home/course"
import {ActivatedRoute, Router} from "@angular/router"
import {CourseContent} from "../model/course-content"
import {CourseNavigation} from "../course-navigation/course-navigation"
import {CookieService} from "ngx-cookie-service"
import {Lecture} from "../model/lecture"
import {KeyboardInputCourseDetails} from "./keyboard-input"
import {CoursesService} from '../service/courses/courses.service'

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'course-details',
    templateUrl: './course-details.component.html',
    styleUrls: ['./course-details.component.css']
})
export class CourseDetailsComponent implements OnInit {
    course: Course = {}
    courseContent?: CourseContent
    navigation = new CourseNavigation()
    currentLecture?: Lecture

    constructor(
        private keyboardInputDetails: KeyboardInputCourseDetails,
        private coursesService: CoursesService,
        private cookieService: CookieService,
        private route: ActivatedRoute,
        private router: Router) {
    }

    @HostListener('window:keydown', ['$event'])
    keyboardInput(event: any) {
        this.keyboardInputDetails.keyboardInput(this, event)
    }

    /**
     * Component initialization.
     * If user is not authenticated then redirection to /sign-in.
     * Otherwise course is fetched from service to initialize to the component.
     */
    ngOnInit() {
        if (this.cookieService.get('Authenticated') !== 'true') {
            this.router.navigate(['/sign-in'])
                .then(() => {
                    window.location.reload()
                })
        }
        this.fetchCourseAndInitView()
    }

    /**
     * Fetch latest course from service (service cache).
     * @private
     */
    private fetchCourseAndInitView() {
        const id = this.route.snapshot.paramMap.get('id')!
        this.coursesService.getCourses()
            .subscribe(courses => {
                for (let i = 0; i < courses.length; i++) {
                    let course = courses[i]
                    if (course.code === id) {
                        this.course = course
                        this.courseContent = JSON.parse(atob(<string>this.course.content))
                        this.findAndScrollToSelectedLecture(this.courseContent)
                        break
                    }
                }
            })
    }

    /**
     * Update lecture navigation state and scroll to current lecture selection.
     * @param lecture Lecture to operate on
     * @private
     */
    private selectAndScrollToLecture(lecture?: Lecture) {
        this.selectLecture(lecture, true, false)
    }

    /**
     * Update lecture navigation state, scroll to, and persist current lecture selection in the service.
     * @param lecture
     * @private
     */
    private selectScrollToAndPersistLecture(lecture?: Lecture) {
        this.selectLecture(lecture, true, true)
    }

    /**
     * Update lecture navigation state and persist current lecture selection in the service.
     * @param lecture
     */
    selectAndPersistLecture(lecture?: Lecture) {
        this.selectLecture(lecture, false, true)
    }

    /**
     * Update lecture navigation state, scroll to, and persist current lecture selection in the service.
     * Additional actions depen on passed flag values.
     * @param selectedLecture
     * @param scroll
     * @param persist
     * @private
     */
    private selectLecture(selectedLecture?: Lecture, scroll: boolean = true, persist: boolean = true): void {
        if (selectedLecture == null) {
            return
        }

        let navigation: CourseNavigation = new CourseNavigation()
        let currentLecture: Lecture

        if (this.courseContent) {
            for (let chapter of this.courseContent.chapters)
                for (let lecture of chapter.lectures) {

                    lecture.selected = false
                    if (navigation.nextLecture != null) {
                        continue
                    }
                    // @ts-ignore
                    if (currentLecture != null) {
                        navigation.nextLecture = lecture
                        this.navigation = navigation
                    } else if (lecture != selectedLecture) {
                        navigation.previousLecture = lecture
                    } else {
                        currentLecture = lecture
                        navigation.currentLecture = lecture
                        navigation.currentLecture.selected = true
                    }
                }
        }

        if (persist == true) {
            this.persisteLectureState(selectedLecture)
        }

        if (scroll === true) {
            this.scrollToElement(selectedLecture.id)
        }

        this.navigation = navigation
    }

    /**
     * Find the selected lecture and scroll to it.
     * @param courseContent Course's content object
     * @private
     */
    private findAndScrollToSelectedLecture(courseContent?: CourseContent): void {
        if (courseContent == null) {
            return
        }
        for (let chapter of courseContent.chapters)
            for (let lecture of chapter.lectures)
                if (lecture.selected) {
                    this.selectAndScrollToLecture(lecture)
                    return
                }
        this.selectAndScrollToLecture(courseContent.chapters[0].lectures[0])
    }

    /**
     * Return selected lecture.
     * @private
     */
    private selectedLecture(): Lecture {
        if (this.courseContent != null) {
            for (let chapter of this.courseContent.chapters)
                for (let lecture of chapter.lectures) {
                    if (lecture.id == this.navigation.currentLecture.id) {
                        return lecture
                    }
                }
        }
        // @ts-ignore
        return undefined
    }

    /**
     * Move to the next lecture handler.
     */
    moveToNextLecture(): CourseNavigation {
        if (this.navigation.nextLecture != null) {
            const nextLecture = this.navigation.nextLecture
            this.selectScrollToAndPersistLecture(nextLecture)
        } else {
            const currentLecture = this.selectedLecture()
            this.selectScrollToAndPersistLecture(currentLecture)
        }

        return this.navigation
    }

    /**
     * Move to the previous lecture handler.
     */
    moveToPreviousLecture(): CourseNavigation {
        if (this.navigation.previousLecture != null) {
            const previousLecture = this.navigation.previousLecture
            this.selectScrollToAndPersistLecture(previousLecture)
        } else {
            const currentLecture = this.selectedLecture()
            this.selectScrollToAndPersistLecture(currentLecture)
        }

        return this.navigation
    }

    /**
     * Mark lecture as Viewed.
     */
    markLectureAsViewed(): void {
        this.markLectureViewedFlag(true)
    }

    /**
     * Mark lecture as Viewed.
     */
    markLectureAsNotViewed(): void {
        this.markLectureViewedFlag(false)
    }

    /**
     * mark lecture as Viewed/Not Viewed.
     * @param viewed Viewed/Not Viewed switch
     * @private
     */
    private markLectureViewedFlag(viewed: boolean): void {
        let lecture: Lecture | null = this.selectedLecture()

        if (lecture != null) {
            lecture.processed = viewed
        }
        this.persisteLectureState(lecture as Lecture)
    }

    /**
     * Switch lecture to Viewed in it's not, and vice-versa.
     */
    switchLectureViewedFlag(): void {
        let lecture: Lecture | null = this.selectedLecture()
        if (lecture != null) {
            lecture.processed = !lecture.processed
            this.selectAndPersistLecture(lecture)
        }
    }

    /**
     * Dynamically calculate current learning propress for given course.
     */
    calculateCourseProgress(): number {
        let allLecturesCount: number = 0
        let processedChaptersCount: number = 0
        if (this.courseContent != null) {
            for (let chapter of this.courseContent.chapters)
                for (let lecture of chapter.lectures) {
                    allLecturesCount++
                    if (lecture.processed) processedChaptersCount++
                }
            return Math.round(processedChaptersCount / allLecturesCount * 100)
        } else {
            return 0
        }
    }

    /**
     * Persist lecture state in the service (an update service cache).
     * @param lecture Lecture to operate on
     * @private
     */
    private persisteLectureState(lecture?: Lecture): void {
        console.log(this.courseContent)
        this.course.content = btoa(JSON.stringify(this.courseContent))
        this.coursesService
            .updateLecture(
                this.course.id || '',
                lecture?.id || '',
                lecture?.processed || false)
            .subscribe(() => this.coursesService.updateCache(this.course))
    }

    /**
     * Move to the previous application page.
     */
    moveBack() {
        this.router.navigate(['/course/' + this.course.code])
    }

    /**
     * Move to application /home page.
     */
    moveToHome() {
        this.router.navigate(['/home'])
    }

    /**
     * Start playing video lecture.
     */
    playVideo() {
        let video = document.querySelector('video')
        if (video) {
            if (video.paused) {
                video.play()
            } else {
                video.pause()
            }
        }
    }

    isVideoPaused() {
        let video = document.querySelector('video')
        return video && video.paused
    }

    isVideoPlaying() {
        let video = document.querySelector('video')
        return video && !video.paused
    }

    /**
     * Return class for selected lecture.
     * @param lecture Lecture to operate on
     */
    getLectureClass(lecture: Lecture): string {
        return lecture.selected ? 'selected' : ''
    }

    /**
     * Scroll to the given html element.
     * @param id HTML element id
     * @private
     */
    private scrollToElement(id: string): void {
        // @ts-ignore
        const lectureElement = document.getElementById(id)
        if (lectureElement != null) {
            lectureElement.scrollIntoView({
                behavior: "auto",
                block: "center",
                inline: "center"
            })
        }
    }
}
