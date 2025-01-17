import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {NavigationItems} from "../course-navigation/navigation-items";
import {CourseProgressComponent} from "../course-progress/course-progress.component";

@Component({
    selector: 'pdf-item',
    templateUrl: './pdf-item.component.html',
    styleUrls: ['./pdf-item.component.css']
})
export class PDFItemComponent implements OnInit {
    @Input()
    navigationItems: NavigationItems = new NavigationItems();

    @Input()
    progress?: CourseProgressComponent;

    constructor(
        private route: ActivatedRoute) {
    }

    ngOnInit(): void {
    }

    width() {
        return window.innerWidth;
    }
}
