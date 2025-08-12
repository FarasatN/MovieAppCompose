package com.farasatnovruzov.movieappcompose.model.question

data class QuestionItem(
    val answer: String,
    val category: String,
    val choices: List<String>,
    val question: String
)