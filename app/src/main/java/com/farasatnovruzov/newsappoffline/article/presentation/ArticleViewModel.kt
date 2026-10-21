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
) : ViewModel(){

    var state by mutableStateOf(ArticleState())
        private set

    fun onAction(action: ArticleAction) {
        when (action) {
            is ArticleAction.LoadArticle -> {
                state = state.copy(isLoading = true)
                loadArticle(action.articleId)
            }
        }
    }

    private fun loadArticle(articleId: String) {
        if (articleId.isBlank()){
            state = state.copy(isError = true)
            return
        }
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            repository.getArticle(articleId).collect { articleResult ->
                when (articleResult) {
                    is NewsResult.Error -> {
                        state = state.copy(
                            isError = true,
                        )
                    }
                    is NewsResult.Success -> {
                        state = state.copy(
                            articles = articleResult.data,
                            isError = false
                        )
                    }
                }
            }
            state = state.copy(isLoading = false)

        }
    }
}
