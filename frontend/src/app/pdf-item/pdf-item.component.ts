import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {NavigationLectures} from "../course-navigation/navigation-lectures";
import {CourseDetailsComponent} from "../course-details/course-details.component";

@Component({
    selector: 'pdf-item',
    templateUrl: './pdf-item.component.html',
    styleUrls: ['./pdf-item.component.css']
})
export class PDFItemComponent implements OnInit {
    @Input()
    navigationItems: NavigationLectures = new NavigationLectures();

    @Input()
    progress?: CourseDetailsComponent;

    constructor(
        private route: ActivatedRoute) {
    }

    ngOnInit(): void {
    }

    width() {
        return window.innerWidth;
    }
}
