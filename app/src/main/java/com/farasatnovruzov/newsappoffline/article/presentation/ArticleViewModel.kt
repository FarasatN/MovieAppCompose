package com.farasatnovruzov.newsappoffline.article.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farasatnovruzov.movieappcompose.R
import com.farasatnovruzov.newsappoffline.core.domain.NewsRepository
import com.farasatnovruzov.newsappoffline.core.domain.NewsResult
import com.farasatnovruzov.newsappoffline.core.util.ResourceProvider
import kotlinx.coroutines.launch

class ArticleViewModel(
    private val repository: NewsRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    var state by mutableStateOf(ArticleState())
        private set

    fun onAction(action: ArticleAction) {
        when (action) {
            is ArticleAction.LoadArticle -> {
                loadArticle(action.articleId)
            }
        }
    }

    private fun loadArticle(articleId: String) {
        if (articleId.isBlank()) {
            state = state.copy(
                isError = true,
                errorMessage = resourceProvider.getString(R.string.invalid_article_id)
            )
            return
        }
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                isError = false,
                errorMessage = null
            )
            repository.getArticle(articleId).collect { articleResult ->
                state = when (articleResult) {
                    is NewsResult.Error -> {
                        state.copy(
                            isError = true,
                            errorMessage = articleResult.error ?: resourceProvider.getString(R.string.article_not_found)
                        )
                    }

                    is NewsResult.Success -> {
                        state.copy(
                            article = articleResult.data,
                            isError = false,
                            errorMessage = null
                        )
                    }
                }
            }
            state = state.copy(isLoading = false)
        }
    }
}
