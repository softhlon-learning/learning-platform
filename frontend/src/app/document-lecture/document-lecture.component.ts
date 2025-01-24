import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {NavigationLectures} from "../course-navigation/navigation-lectures";
import {CourseDetailsComponent} from "../course-details/course-details.component";

@Component({
    selector: 'document-lecture',
    templateUrl: './document-lecture.component.html',
    styleUrls: ['./document-lecture.component.css']
})
export class DocumentLectureComponent implements OnInit {
    @Input()
    navigationItems: NavigationLectures = new NavigationLectures();

    @Input()
    progress?: CourseDetailsComponent;

    @Input()
    coursePath: string = '';

    constructor() {
    }

    ngOnInit(): void {
    }

    width() {
        return window.innerWidth;
    }
}
