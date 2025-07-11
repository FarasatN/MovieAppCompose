package com.farasatnovruzov.movieappcompose.network

import com.farasatnovruzov.movieappcompose.model.Question
import retrofit2.http.GET
import javax.inject.Singleton


@Singleton
interface QuestionApi {

    @GET("world.json")
    suspend fun getAllQuestions(): Question
}