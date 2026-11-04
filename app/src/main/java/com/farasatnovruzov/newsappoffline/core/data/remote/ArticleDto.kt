package com.farasatnovruzov.newsappoffline.core.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ArticleDto(
    @SerialName("ai_org")
    val aiOrg: JsonElement? = null,

    @SerialName("ai_region")
    val aiRegion: JsonElement? = null,

    @SerialName("ai_summary")
    val aiSummary: JsonElement? = null,

    @SerialName("ai_tag")
    val aiTag: JsonElement? = null,

    @SerialName("article_id")
    val articleId: String? = null,

    val category: List<String?>? = null,

    val content: String? = null,

    val country: List<String?>? = null,

    val creator: List<String?>? = null,

    val datatype: String? = null,

    val description: String? = null,

    val duplicate: Boolean? = null,

    @SerialName("fetched_at")
    val fetchedAt: String? = null,

    @SerialName("image_url")
    val imageUrl: String? = null,

    val keywords: List<String?>? = null,

    val language: String? = null,

    val link: String? = null,

    val pubDate: String? = null,

    val pubDateTZ: String? = null,

    val sentiment: String? = null,

    @SerialName("sentiment_stats")
    val sentimentStats: JsonElement? = null,

    @SerialName("source_icon")
    val sourceIcon: String? = null,

    @SerialName("source_id")
    val sourceId: String? = null,

    @SerialName("source_name")
    val sourceName: String? = null,

    @SerialName("source_priority")
    val sourcePriority: Int? = null,

    @SerialName("source_url")
    val sourceUrl: String? = null,

    val title: String? = null,

    @SerialName("video_url")
    val videoUrl: String? = null
)