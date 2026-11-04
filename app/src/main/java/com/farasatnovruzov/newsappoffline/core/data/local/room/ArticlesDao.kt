package com.farasatnovruzov.newsappoffline.core.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert


@Dao
interface ArticlesDao {

    @Query("SELECT * FROM article_entity WHERE language = :languageCode OR language IS NULL")
    suspend fun getArticleList(languageCode: String): List<ArticleEntity>

    @Query("DELETE FROM article_entity WHERE language = :languageCode OR language IS NULL")
    suspend fun clearDatabaseByLanguage(languageCode: String)

    // TRANZAKSİYA: Təmizləmə və yazma əməliyyatı tək bir atomar addımda icra olunur
    @Transaction
    suspend fun clearAndUpsert(languageCode: String, articleList: List<ArticleEntity>) {
        clearDatabaseByLanguage(languageCode)
        upsertArticleList(articleList)
    }
    @Upsert
    suspend fun upsertArticleList(articleList: List<ArticleEntity>)

    @Query("SELECT * FROM article_entity WHERE articleId = :articleId")
    suspend fun getArticle(articleId: String): ArticleEntity?

    @Query("DELETE FROM article_entity")
    suspend fun clearDatabase()

    @Query("""
    SELECT * FROM article_entity 
    WHERE title LIKE '%' || :query || '%' ESCAPE '\' 
    OR description LIKE '%' || :query || '%' ESCAPE '\'
""")
    suspend fun searchArticle(query: String): List<ArticleEntity>

}