package com.farasatnovruzov.movieappcompose.network.booksociety

import com.farasatnovruzov.movieappcompose.model.booksociety.Book
import com.farasatnovruzov.movieappcompose.model.booksociety.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface BooksApi {

    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): Book

    //Interceptor oldugu ucun buna ehtiyac qalmadi
//    @GET("volumes")
//    suspend fun getAllBooks(
//        @Query("q") query: String,
//        @Query("key") apiKey: String = Constants.API_KEY_BOOK_SOCIETY // Susmaya görə açarı bura qoyuruq
//    ): Book



    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId: String): Item



}