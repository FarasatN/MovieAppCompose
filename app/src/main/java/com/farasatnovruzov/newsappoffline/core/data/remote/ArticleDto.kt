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

    @SerialName("category")
    val category: List<String?>? = null,

    @SerialName("content")
    val content: String? = null,

    @SerialName("country")
    val country: List<String?>? = null,

    @SerialName("creator")
    val creator: List<String?>? = null,

    @SerialName("datatype")
    val datatype: String? = null,

    @SerialName("description")
    val description: String? = null,

    @SerialName("duplicate")
    val duplicate: Boolean? = null,

    @SerialName("fetched_at")
    val fetchedAt: String? = null,

    @SerialName("image_url")
    val imageUrl: String? = null,

    @SerialName("keywords")
    val keywords: List<String?>? = null,

    @SerialName("language")
    val language: String? = null,

    @SerialName("link")
    val link: String? = null,

    @SerialName("pubDate")
    val pubDate: String? = null,

    @SerialName("pubDateTZ")
    val pubDateTZ: String? = null,

    @SerialName("sentiment")
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

    @SerialName("title")
    val title: String? = null,

    @SerialName("video_url")
    val videoUrl: String? = null
)