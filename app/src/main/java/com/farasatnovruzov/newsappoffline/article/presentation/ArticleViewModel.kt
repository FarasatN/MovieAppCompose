package com.farasatnovruzov.newsappoffline.article.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farasatnovruzov.newsappoffline.core.domain.NewsError
import com.farasatnovruzov.newsappoffline.core.domain.NewsRepository
import com.farasatnovruzov.newsappoffline.core.domain.NewsResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ArticleViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    var state by mutableStateOf(ArticleState())
        private set

    private var loadJob: Job? = null

    fun onAction(action: ArticleAction) {
        when (action) {
            is ArticleAction.LoadArticle -> loadArticle(action.articleId)
        }
    }

    private fun loadArticle(articleId: String) {
        if (articleId.isBlank()) {
            state = state.copy(error = NewsError.InvalidArticleId)
            return
        }

        // Retry-a ardıcıl basanda paralel eyni sorğular yığılmasın
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            repository.getArticle(articleId).collect { result ->
                state = when (result) {
                    is NewsResult.Error -> state.copy(error = result.error)
                    is NewsResult.Success -> state.copy(article = result.data, error = null)
                }
            }

            state = state.copy(isLoading = false)
        }
    }
}
