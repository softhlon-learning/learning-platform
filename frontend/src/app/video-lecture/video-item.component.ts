import {Component, ElementRef, Input, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
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

    constructor(
        private route: ActivatedRoute) {
    }

    ngOnInit(): void {
    }

    ngOnChanges(changes: SimpleChanges) {
        if (this.video != null) {
            this.video.nativeElement.load();
        }
    }

    protected readonly alert = alert;
}
