import {Component, Input, OnInit} from '@angular/core';
import {PlatformService} from '../service/platform.service';
import {ActivatedRoute} from "@angular/router";
import {NavigationLectures} from "./navigation-lectures";
import {CourseDetailsComponent} from "../course-details/course-details.component";

@Component({
    selector: 'course-navigation',
    templateUrl: './course-navigation.component.html',
    styleUrls: ['./course-navigation.component.css']
})
export class CourseNavigationComponent implements OnInit {
    @Input()
    navigationItems: NavigationLectures = new NavigationLectures();

    @Input()
    progress?: CourseDetailsComponent;

    constructor() {
    }

    ngOnInit(): void {
    }

    previous(): void {
        if (this.progress != null) {
            this.progress.previous();
        }
    }

    next(): void {
        if (this.progress != null) {
            this.progress.next();
        }
    }

    markAsViewed(): void {
        this.progress?.markAsViewed();
    }

    markAsNotViewed(): void {
        this.progress?.markAsNotViewed();
    }
}
