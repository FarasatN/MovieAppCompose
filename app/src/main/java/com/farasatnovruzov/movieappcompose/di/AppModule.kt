package com.farasatnovruzov.movieappcompose.di

import android.content.Context
import androidx.room.Room
import com.farasatnovruzov.movieappcompose.data.NoteDatabase
import com.farasatnovruzov.movieappcompose.data.NoteDatabaseDao
import com.farasatnovruzov.movieappcompose.network.QuestionApi
import com.farasatnovruzov.movieappcompose.repository.NoteRepository
import com.farasatnovruzov.movieappcompose.repository.QuestionRepository
import com.farasatnovruzov.movieappcompose.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideNotesDao(noteDatabase: NoteDatabase): NoteDatabaseDao=
        noteDatabase.noteDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NoteDatabase=
        Room.databaseBuilder(
                context,
                NoteDatabase::class.java,
                "notes_db"
            ).fallbackToDestructiveMigration(false).build()

    @Singleton
    @Provides
    fun provideQuestionApi(): QuestionApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApi::class.java)
    }

    @Singleton
    @Provides
    fun provideQuestionRepository(api: QuestionApi) = QuestionRepository(api)


}