package com.farasatnovruzov.newsappoffline.article.presentation

sealed interface ArticleAction {
    data class LoadArticle(val articleId: String) : ArticleAction
//    data class SearchArticle(val query: String) : ArticleAction

}