package com.farasatnovruzov.newsappoffline.news.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farasatnovruzov.newsappoffline.core.domain.NewsCategory
import com.farasatnovruzov.newsappoffline.core.domain.NewsLanguage
import com.farasatnovruzov.newsappoffline.core.domain.NewsRepository
import com.farasatnovruzov.newsappoffline.core.domain.NewsResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    var state by mutableStateOf(NewsState())
        private set

    private var searchJob: Job? = null

    init {
        loadNews()
    }

    fun onAction(action: NewsAction) {
        when (action) {
            is NewsAction.Paginate -> paginate()
            is NewsAction.OnSearchQueryChange -> onSearchQueryChange(action.query)
            is NewsAction.OnLanguageSelect -> onLanguageSelect(action.language)
            is NewsAction.OnCategorySelect -> onCategorySelect(action.category)
            is NewsAction.ClearSearch -> clearSearch()
            is NewsAction.Refresh -> loadNews()
        }
    }

    private fun loadNews() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                isError = false,
                errorMessage = null
            )

            newsRepository.getNews(
                languageCode = state.selectedLanguage.code,
                category = state.selectedCategory.code
            ).collect { result ->
                state = when (result) {
                    is NewsResult.Error -> {
                        state.copy(
                            isError = true,
                            errorMessage = result.error ?: "Məlumat yüklənmədi"
                        )
                    }

                    is NewsResult.Success -> {
                        val articles = result.data?.articles?.filterNotNull() ?: emptyList()
                        state.copy(
                            isError = false,
                            errorMessage = null,
                            articleList = articles.distinctBy { it.articleId ?: it.title },
                            nextPage = result.data?.nextPage
                        )
                    }
                }
            }

            state = state.copy(isLoading = false)
        }
    }

    private fun paginate() {
        if (state.isLoading || state.isSearchActive) return
        val nextPage = state.nextPage ?: return

        viewModelScope.launch {
            state = state.copy(isLoading = true)

            newsRepository.paginate(
                nextPage = nextPage,
                languageCode = state.selectedLanguage.code,
                category = state.selectedCategory.code
            ).collect { result ->
                state = when (result) {
                    is NewsResult.Error -> {
                        state.copy(
                            isError = true,
                            errorMessage = result.error ?: "Səhifələmə xətası"
                        )
                    }

                    is NewsResult.Success -> {
                        val newArticles = result.data?.articles?.filterNotNull() ?: emptyList()
                        val combined = (state.articleList + newArticles).distinctBy { it.articleId ?: it.title }
                        state.copy(
                            isError = false,
                            errorMessage = null,
                            articleList = combined,
                            nextPage = result.data?.nextPage
                        )
                    }
                }
            }

            state = state.copy(isLoading = false)
        }
    }

    private fun onLanguageSelect(language: NewsLanguage) {
        if (state.selectedLanguage == language) return
        state = state.copy(
            selectedLanguage = language,
            articleList = emptyList(),
            searchArticleList = emptyList()
        )

        if (state.isSearchActive) {
            executeSearch(state.searchQuery)
        } else {
            loadNews()
        }
    }

    private fun onCategorySelect(category: NewsCategory) {
        if (state.selectedCategory == category) return
        state = state.copy(
            selectedCategory = category,
            articleList = emptyList(),
            searchArticleList = emptyList()
        )

        if (state.isSearchActive) {
            executeSearch(state.searchQuery)
        } else {
            loadNews()
        }
    }

    private fun onSearchQueryChange(query: String) {
        state = state.copy(searchQuery = query)
        searchJob?.cancel()

        if (query.isBlank()) {
            state = state.copy(
                searchArticleList = emptyList(),
                isSearchingLoading = false
            )
            return
        }

        searchJob = viewModelScope.launch {
            delay(500.milliseconds) // Debounce typing (500ms)
            executeSearch(query)
        }
    }

    private fun executeSearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            state = state.copy(isSearchingLoading = true)
            newsRepository.searchNews(
                query = query,
                languageCode = state.selectedLanguage.code,
                category = state.selectedCategory.code
            ).collect { result ->
                state = when (result) {
                    is NewsResult.Error -> {
                        state.copy(
                            searchArticleList = emptyList(),
                            isSearchingLoading = false
                        )
                    }

                    is NewsResult.Success -> {
                        state.copy(
                            searchArticleList = result.data ?: emptyList(),
                            isSearchingLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun clearSearch() {
        searchJob?.cancel()
        state = state.copy(
            searchQuery = "",
            searchArticleList = emptyList(),
            isSearchingLoading = false
        )
    }
}
