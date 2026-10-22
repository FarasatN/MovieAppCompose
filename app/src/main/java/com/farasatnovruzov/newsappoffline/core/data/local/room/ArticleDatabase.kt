package com.farasatnovruzov.newsappoffline.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ArticleTypeConverters::class)
abstract class ArticleDatabase: RoomDatabase() {

    abstract val dao: ArticlesDao

}