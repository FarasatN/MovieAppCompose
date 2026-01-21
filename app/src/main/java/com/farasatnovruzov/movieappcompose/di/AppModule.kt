package com.farasatnovruzov.movieappcompose.di

import android.content.Context
import androidx.room.Room
import com.farasatnovruzov.movieappcompose.data.note.NoteDatabase
import com.farasatnovruzov.movieappcompose.data.note.NoteDatabaseDao
import com.farasatnovruzov.movieappcompose.data.weather.WeatherDao
import com.farasatnovruzov.movieappcompose.data.weather.WeatherDatabase
import com.farasatnovruzov.movieappcompose.network.booksociety.BooksApi
import com.farasatnovruzov.movieappcompose.network.question.QuestionApi
import com.farasatnovruzov.movieappcompose.network.weather.WeatherApi
import com.farasatnovruzov.movieappcompose.repository.booksociety.BookSocietyRepository
import com.farasatnovruzov.movieappcompose.repository.question.QuestionRepository
import com.farasatnovruzov.movieappcompose.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
//class AppModule {
object AppModule { //hilt best practice used with "object"


    @Singleton
    @Provides
    fun provideBookRepository(api: BooksApi) = BookSocietyRepository(api)

    //Book Society
    @Provides
    @Singleton
    fun provideBookApi(): BooksApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_BOOK_SOCIETY)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }


    //Weather
    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao = weatherDatabase.weatherDao()

    @Provides
    @Singleton
    fun provideWeatherAppDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_db"
        ).fallbackToDestructiveMigration(false).build()



    //Note
    @Provides
    @Singleton
    fun provideNotesDao(noteDatabase: NoteDatabase): NoteDatabaseDao=
        noteDatabase.noteDao()

    @Provides
    @Singleton
    fun provideNoteAppDatabase(@ApplicationContext context: Context): NoteDatabase=
        Room.databaseBuilder(
                context,
                NoteDatabase::class.java,
                "notes_db"
            ).fallbackToDestructiveMigration(false).build()


    //Trivia
    @Provides
    @Singleton
    fun provideQuestionApi(): QuestionApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApi::class.java)
    }

    @Provides
    @Singleton
    fun provideQuestionRepository(api: QuestionApi) = QuestionRepository(api)


}