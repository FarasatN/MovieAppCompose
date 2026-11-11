package com.farasatnovruzov.newsappoffline.core.data.remote

object NewsApiConfig {
    const val BASE_URL = "https://newsdata.io/api/1/latest"

    /** Açar URL-də deyil header-də göndərilir: log-lara, brauzer tarixçəsinə və proxy-lərə düşmür. */
    const val HEADER_API_KEY = "X-ACCESS-KEY"

    const val PARAM_QUERY = "q"
    const val PARAM_LANGUAGE = "language"
    const val PARAM_CATEGORY = "category"
    const val PARAM_PAGE = "page"
    const val PARAM_ARTICLE_ID = "id"

    const val STATUS_ERROR = "error"
}
