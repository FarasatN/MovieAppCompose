package com.farasatnovruzov.newsappoffline.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ArticleDatabase: RoomDatabase() {

    abstract val dao: ArticlesDao

}