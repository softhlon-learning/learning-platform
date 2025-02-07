// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {QuizAnswer} from "./quiz-answer"

export class QuizQuestion {
    text: string = ''
    hint: string = ''
    answers: Array<QuizAnswer> = []
    correctAnswer: number = 0
    answered: number = 0
}
