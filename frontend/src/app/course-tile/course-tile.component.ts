import {Component, Input, OnInit} from '@angular/core';
import {Course} from "../home/course";

@Component({
    selector: 'course-tile',
    templateUrl: './course-tile.component.html',
    styleUrls: ['./course-tile.component.css']
})
export class CourseTileComponent implements OnInit {
    @Input()
    course!: Course; // Replace 'any' with the correct type when defined or imported

    ngOnInit(): void {
    }

    constructor() {
    }
}
