package com.farasatnovruzov.newsappoffline.core.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
abstract class ArticlesDao {

    /**
     * Seçilmiş dilin xəbərləri. [category] verilibsə, yalnız həmin kateqoriyaya aid olanlar
     * (kateqoriyalar JSON siyahı kimi saxlanır: ["business","top"]).
     */
    @Query(
        """
        SELECT * FROM article_entity
        WHERE (language = :languageCode OR language IS NULL)
        AND (:category IS NULL OR category LIKE '%"' || :category || '"%')
        ORDER BY pubDate DESC
        """
    )
    abstract suspend fun getArticleList(languageCode: String, category: String?): List<ArticleEntity>

    @Query("DELETE FROM article_entity WHERE language = :languageCode OR language IS NULL")
    abstract suspend fun clearDatabaseByLanguage(languageCode: String)

    @Upsert
    abstract suspend fun upsertArticleList(articleList: List<ArticleEntity>)

    @Query("SELECT * FROM article_entity WHERE articleId = :articleId")
    abstract suspend fun getArticle(articleId: String): ArticleEntity?

    /** [query] əvvəlcədən `escapeSqlLike()` ilə təmizlənməlidir. */
    @Query(
        """
        SELECT * FROM article_entity
        WHERE (title LIKE '%' || :query || '%' ESCAPE '\'
               OR description LIKE '%' || :query || '%' ESCAPE '\')
        AND (language = :languageCode OR language IS NULL)
        ORDER BY pubDate DESC
        """
    )
    abstract suspend fun searchArticle(query: String, languageCode: String): List<ArticleEntity>

    /** Təmizləmə + yazma tək tranzaksiyada: arada proses ölsə cache boş qalmır. */
    @Transaction
    open suspend fun clearAndUpsert(languageCode: String, articleList: List<ArticleEntity>) {
        clearDatabaseByLanguage(languageCode)
        upsertArticleList(articleList)
    }
}
