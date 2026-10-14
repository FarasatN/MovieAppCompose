package com.farasatnovruzov.newsappoffline.core.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface ArticlesDao {

    @Query("SELECT * FROM article_entity")
    suspend fun getArticleList(): List<ArticleEntity>

    @Upsert
    suspend fun upsertArticleList(articleList: List<ArticleEntity>)

    @Query("SELECT * FROM article_entity WHERE articleId = :articleId")
    suspend fun getArticle(articleId: String): ArticleEntity?

    @Query("DELETE FROM article_entity")
    suspend fun clearDatabase()

    @Query("SELECT * FROM article_entity WHERE title LIKE '%' || :query || '%'")
    suspend fun searchArticle(query: String): List<ArticleEntity>


}