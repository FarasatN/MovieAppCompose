package com.farasatnovruzov.newsappoffline.article.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farasatnovruzov.newsappoffline.core.domain.NewsRepository
import com.farasatnovruzov.newsappoffline.core.domain.NewsResult
import kotlinx.coroutines.launch

class ArticleViewModel(
    private val repository: NewsRepository,
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
                errorMessage = "Keçərsiz məqalə ID"
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
                            errorMessage = articleResult.error ?: "Məqalə tapılmadı"
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
