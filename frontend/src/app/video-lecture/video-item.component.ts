import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {NavigationLectures} from "../course-navigation/navigation-lectures";
import {CourseDetailsComponent} from "../course-details/course-details.component";

@Component({
    selector: 'video-lecture',
    templateUrl: './video-item.component.html',
    styleUrls: ['./video-item.component.css']
})
export class VideoItemComponent implements OnInit {
    @ViewChild('video') video?: ElementRef;

    @Input()
    navigationItems: NavigationLectures = new NavigationLectures();

    @Input()
    progress?: CourseDetailsComponent;

    @Input()
    coursePath?: string;

    constructor() {
    }

    ngOnInit(): void {
    }

    ngOnChanges() {
        if (this.video != null) {
            this.video.nativeElement.load();
        }
    }
}
