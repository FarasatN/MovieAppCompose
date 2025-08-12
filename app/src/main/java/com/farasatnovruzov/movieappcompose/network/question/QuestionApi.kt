package com.farasatnovruzov.movieappcompose.network.question

import com.farasatnovruzov.movieappcompose.model.question.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {

    @GET("world.json")
    suspend fun getAllQuestions(): Question
}