package com.farasatnovruzov.movieappcompose.model.booksociety

data class Book(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)