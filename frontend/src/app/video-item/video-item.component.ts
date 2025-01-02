import {Component, ElementRef, Input, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {NavigationItems} from "../course-navigation/navigation-items";
import {CourseProgressComponent} from "../course-progress/course-progress.component";

@Component({
  selector: 'video-item',
  templateUrl: './video-item.component.html',
  styleUrls: ['./video-item.component.css']
})
export class VideoItemComponent implements OnInit {
  @ViewChild('video')video?:ElementRef;

  @Input()
  navigationItems: NavigationItems = new NavigationItems();

  @Input()
  progress?: CourseProgressComponent;

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
