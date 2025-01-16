import {Component, Input, OnInit} from '@angular/core';
import {PlatformService} from '../service/platform.service';
import {ActivatedRoute} from "@angular/router";
import {NavigationItems} from "./navigation-items";
import {CourseProgressComponent} from "../course-progress/course-progress.component";

@Component({
    selector: 'course-navigation',
    templateUrl: './course-navigation.component.html',
    styleUrls: ['./course-navigation.component.css']
})
export class CourseNavigationComponent implements OnInit {
    @Input()
    navigationItems: NavigationItems = new NavigationItems();

    @Input()
    progress?: CourseProgressComponent;

    constructor(
        private coursesService: PlatformService,
        private route: ActivatedRoute) {
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
