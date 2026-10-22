package com.farasatnovruzov.newsappoffline.core.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_entity")
data class ArticleEntity(
    val aiOrg: String?,
    val aiRegion: String?,
    val aiSummary: String?,
    val aiTag: String?,

    @PrimaryKey(autoGenerate = false)
    val articleId: String,

    val category: List<String?>?,
    val content: String?,
    val country: List<String?>?,
    val creator: List<String?>?,
    val datatype: String?,
    val description: String?,
    val duplicate: Boolean?,
    val fetchedAt: String?,
    val imageUrl: String?,
    val keywords: List<String?>?,
    val language: String?,
    val link: String?,
    val pubDate: String?,
    val pubDateTZ: String?,
    val sentiment: String?,
    val sentimentStats: String?,
    val sourceIcon: String?,
    val sourceId: String?,
    val sourceName: String?,
    val sourcePriority: Int?,
    val sourceUrl: String?,
    val title: String?,
    val videoUrl: String?
)