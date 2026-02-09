package com.farasatnovruzov.movieappcompose.model.booksociety

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

data class MBook(
    @Exclude var id: String? = null,
    var title: String? = null,
    var authors: String? = null,
    var notes: String? = null,
    var bookImage: String? = null,
    @get:PropertyName("book_photo_url")
    @set:PropertyName("book_photo_url")
    var photoUrl: String? = null,
    var categories: String? = null,
//    var categories: List<String>? = null,
    //published_date
    @get:PropertyName("published_date")
    @set:PropertyName("published_date")
    var publishedDate: String? = null,
    var pageCount: String? = null,
//    var pageCount: Int? = null,
    var rating: Double? = null,
    var description: String? = null,
//    var isFavorite: Boolean = false,
//    var isReading: Boolean = false,
    var startReading: String? = null,
    var finishedReading: String? = null,
    var userId: String? = null,
    var googleBookId: String? = null,
    var smallThumbnail: String? = null,
    var thumbnail: String? = null,
    var previewLink: String? = null,
    var infoLink: String? = null,
    var canonicalVolumeLink: String? = null,
    var subtitle: String? = null,
    var publisher: String? = null
)
