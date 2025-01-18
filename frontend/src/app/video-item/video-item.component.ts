import {Component, ElementRef, Input, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {NavigationLectures} from "../course-navigation/navigation-lectures";
import {CourseProgressComponent} from "../course-progress/course-progress.component";

@Component({
    selector: 'video-item',
    templateUrl: './video-item.component.html',
    styleUrls: ['./video-item.component.css']
})
export class VideoItemComponent implements OnInit {
    @ViewChild('video') video?: ElementRef;

    @Input()
    navigationItems: NavigationLectures = new NavigationLectures();

    @Input()
    progress?: CourseProgressComponent;

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
}
