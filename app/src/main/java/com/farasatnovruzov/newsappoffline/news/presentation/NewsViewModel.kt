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

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    var state by mutableStateOf(NewsState())
        private set

    /** Əsas yükləmə və səhifələmə işi. Yeni iş başlayanda köhnəsi ləğv edilir (yarış problemi olmasın). */
    private var newsJob: Job? = null
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
        newsJob?.cancel()
        newsJob = viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            newsRepository.getNews(
                languageCode = state.selectedLanguage.code,
                category = state.selectedCategory.code
            ).collect { result ->
                state = when (result) {
                    is NewsResult.Error -> state.copy(error = result.error)
                    is NewsResult.Success -> state.copy(
                        error = null,
                        articleList = result.data.articles.distinctBy { it.articleId },
                        nextPage = result.data.nextPage
                    )
                }
            }

            state = state.copy(isLoading = false)
        }
    }

    private fun paginate() {
        if (state.isLoading || state.isSearchActive) return
        val nextPage = state.nextPage ?: return

        newsJob?.cancel()
        newsJob = viewModelScope.launch {
            state = state.copy(isLoading = true)

            newsRepository.paginate(
                nextPage = nextPage,
                languageCode = state.selectedLanguage.code,
                category = state.selectedCategory.code
            ).collect { result ->
                state = when (result) {
                    is NewsResult.Error -> state.copy(error = result.error)
                    is NewsResult.Success -> state.copy(
                        error = null,
                        articleList = (state.articleList + result.data.articles).distinctBy { it.articleId },
                        nextPage = result.data.nextPage
                    )
                }
            }

            state = state.copy(isLoading = false)
        }
    }

    private fun onLanguageSelect(language: NewsLanguage) {
        if (state.selectedLanguage == language) return
        onFilterChanged { copy(selectedLanguage = language) }
    }

    private fun onCategorySelect(category: NewsCategory) {
        if (state.selectedCategory == category) return
        onFilterChanged { copy(selectedCategory = category) }
    }

    /** Dil və kateqoriya dəyişikliyi üçün ortaq məntiq (DRY). */
    private fun onFilterChanged(transform: NewsState.() -> NewsState) {
        // Köhnə filtrlə gedən sorğunun nəticəsi yeni siyahını pozmasın
        newsJob?.cancel()
        state = state.transform().copy(
            articleList = emptyList(),
            searchArticleList = emptyList(),
            nextPage = null,
            isLoading = false
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
            resetSearchResults()
            return
        }

        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_MS) // Debounce: hər hərfdə sorğu göndərmirik
            performSearch(query)
        }
    }

    private fun executeSearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch { performSearch(query) }
    }

    private suspend fun performSearch(query: String) {
        state = state.copy(isSearchingLoading = true)

        newsRepository.searchNews(
            query = query,
            languageCode = state.selectedLanguage.code,
            category = state.selectedCategory.code
        ).collect { result ->
            state = when (result) {
                is NewsResult.Error -> state.copy(
                    searchArticleList = emptyList(),
                    isSearchingLoading = false
                )

                is NewsResult.Success -> state.copy(
                    searchArticleList = result.data,
                    isSearchingLoading = false
                )
            }
        }
    }

    private fun clearSearch() {
        searchJob?.cancel()
        state = state.copy(searchQuery = "")
        resetSearchResults()
    }

    /**
     * Axtarış nəticələrini təmizləyir. Axtarış zamanı filtr dəyişdirilibsə əsas siyahı boş qalıb:
     * bu halda axtarışdan çıxanda siyahını yenidən yükləyirik (əks halda ekran boş qalırdı).
     */
    private fun resetSearchResults() {
        state = state.copy(searchArticleList = emptyList(), isSearchingLoading = false)
        if (state.articleList.isEmpty() && !state.isLoading) {
            loadNews()
        }
    }

    private companion object {
        const val SEARCH_DEBOUNCE_MS = 500L
    }
}
