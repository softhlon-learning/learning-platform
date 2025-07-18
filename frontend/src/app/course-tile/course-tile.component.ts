// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, Input, OnInit} from '@angular/core';
import {Course} from "../home/course";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'course-tile',
    templateUrl: './course-tile.component.html',
    styleUrls: ['./course-tile.component.css'],
    standalone: false
})
export class CourseTileComponent implements OnInit {
    @Input()
    course!: Course // Replace 'any' with the correct type when defined or imported

    constructor() {
    }

    ngOnInit(): void {
    }
}
