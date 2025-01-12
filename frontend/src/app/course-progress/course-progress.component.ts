import {Component, HostListener, OnInit} from '@angular/core';
import {Course} from "../courses/course";
import {CoursesService} from '../courses/courses.service';
import {ActivatedRoute} from "@angular/router";
import {CourseContent} from "../course-content/course-content";
import {Item} from "../course-content/item";
import {NavigationItems} from "../course-navigation/navigation-items";
import {Chapter} from '../course-content/chapter';

@Component({
    selector: 'course-progress',
    templateUrl: './course-progress.component.html',
    styleUrls: ['./course-progress.component.css']
})
export class CourseProgressComponent implements OnInit {
    course: Course = {};
    courseContent?: CourseContent;
    navigationItems = new NavigationItems();
    itemToChapter = new Map<Item, Chapter>();

    constructor(
        private coursesService: CoursesService,
        private route: ActivatedRoute) {
    }

    @HostListener('window:keydown', ['$event'])
    keyboardInput(event: any) {
        // event.preventDefault();
        event.stopPropagation();

        if (event.code == 'ArrowDown' || event.code == 'ArrowRight') {
            this.next();
        }

        if (event.code == 'ArrowUp' || event.code == 'ArrowLeft') {
            this.previous()
        }
    }

    ngOnInit() {
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
                this.initializeMap();
            })
    }

    setItem(selectedItem: Item): void {
        let tempNavigationItems: NavigationItems = new NavigationItems();
        let currentItem: Item;

        if (this.courseContent) {
            for (let section of this.courseContent.sections)
                for (let chapter of section.chapters)
                    if (chapter.items != null)
                        for (let item of chapter.items) {
                            item.selected = false;

                            if (tempNavigationItems.nextItem != null) {
                                continue;
                            }

                            // @ts-ignore
                            if (currentItem != null) {
                                tempNavigationItems.nextItem = item;
                                this.navigationItems = tempNavigationItems;
                            } else if (item != selectedItem) {
                                tempNavigationItems.previousItem = item;
                            } else {
                                currentItem = item;
                                tempNavigationItems.currentItem = item;
                                tempNavigationItems.currentItem.selected = true;
                            }
                        }
        }
        this.navigationItems = tempNavigationItems;
    }

    findCurrentItem(courseContent: CourseContent): void {
        for (let section of courseContent.sections)
            for (let chapter of section.chapters)
                if (chapter.items != null)
                    for (let item of chapter.items)
                        if (item.processed) {
                            this.setItem(item);
                            return;
                        }
    }

    getCurrentItem(): Item | null {
        if (this.courseContent != null) {
            for (let section of this.courseContent.sections)
                for (let chapter of section.chapters)
                    if (chapter.items != null)
                        for (let item of chapter.items)
                            if (item.id == this.navigationItems.currentItem.id) {
                                this.setItem(item);
                                return item;
                            }
        }
        return null;
    }

    initializeMap(): void {
        if (this.courseContent != null) {
            for (let section of this.courseContent.sections)
                for (let chapter of section.chapters)
                    if (chapter.items != null)
                        for (let item of chapter.items)
                            this.itemToChapter.set(item, chapter);
        }
    }

    next(): NavigationItems {
        if (this.navigationItems.nextItem != null) {
            this.setItem(this.navigationItems.nextItem);
        }
        this.scrollToElement(this.navigationItems.currentItem.id);
        return this.navigationItems;
    }

    previous(): NavigationItems {
        if (this.navigationItems.previousItem != null) {
            this.setItem(this.navigationItems.previousItem);
        }
        this.scrollToElement(this.navigationItems.currentItem.id);
        return this.navigationItems;
    }

    getClass(item: Item): string {
        return item.selected ? 'selected' : '';
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
        let item: Item | null = this.getCurrentItem();
        if (item != null) {
            item.processed = true;
            this.updateChapterProcessed(item);
        }
        this.updateCourse();
    }

    markAsNotViewed(): void {
        let item: Item | null = this.getCurrentItem();
        if (item != null) {
            item.processed = false;
            this.updateChapterProcessed(item);
        }
        this.updateCourse();
    }

    updateChapterProcessed(selectedItem: Item): void {
        if (!selectedItem.processed) {
            const chapter = this["itemToChapter"].get(selectedItem);
            if (chapter) {
                chapter.processed = false;
                return;
            }
        }

        const chapter = this["itemToChapter"].get(selectedItem);
        if (chapter) {
            for (let item of chapter.items) {
                if (!item.processed) {
                    chapter.processed = false;
                    return;
                }
            }
            chapter.processed = true;
            return;
        }
    }

    courseProgress(): number {
        let allItemsCount: number = 0;
        let processedItemsCount: number = 0;
        if (this.courseContent != null) {
            for (let section of this.courseContent.sections)
                for (let chapter of section.chapters)
                    if (chapter.items != null)
                        for (let item of chapter.items) {
                            allItemsCount++;
                            if (item.processed) processedItemsCount++;
                        }
        }

        return Math.round(processedItemsCount / allItemsCount * 100);
    }

    updateCourse(): void {
        let courseContentB64 = btoa(JSON.stringify(this.courseContent));
        this.coursesService.updateCourse(this.course.id ?? '', courseContentB64).subscribe();
        this.update();
    }

    update() {
        console.log("Updating courses");
        this.coursesService.refreshCourses().subscribe(
            item => this.getCourse()
        );
    }
}
