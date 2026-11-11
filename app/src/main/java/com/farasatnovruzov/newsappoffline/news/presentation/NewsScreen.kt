package com.farasatnovruzov.newsappoffline.news.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farasatnovruzov.movieappcompose.R
import com.farasatnovruzov.newsappoffline.core.domain.Article
import com.farasatnovruzov.newsappoffline.core.domain.NewsCategory
import com.farasatnovruzov.newsappoffline.core.domain.NewsLanguage
import com.farasatnovruzov.newsappoffline.core.presentation.components.ErrorContent
import com.farasatnovruzov.newsappoffline.core.presentation.util.asString
import com.farasatnovruzov.newsappoffline.news.presentation.components.ArticleItem
import com.farasatnovruzov.newsappoffline.news.presentation.components.FilterChipRow
import com.farasatnovruzov.newsappoffline.news.presentation.components.PaginationEffect
import com.farasatnovruzov.newsappoffline.news.presentation.util.displayName
import com.farasatnovruzov.newsappoffline.news.presentation.util.emoji
import com.farasatnovruzov.newsappoffline.news.presentation.util.flagEmoji
import com.farasatnovruzov.newsappoffline.news.presentation.util.titleRes
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreenCore(
    onArticleClick: (String) -> Unit,
    viewModel: NewsViewModel = koinViewModel()
) {
    NewsScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onArticleClick = onArticleClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsScreen(
    state: NewsState,
    onAction: (NewsAction) -> Unit,
    onArticleClick: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            NewsTopBar(
                state = state,
                onAction = onAction,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        NewsContent(
            state = state,
            onAction = onAction,
            onArticleClick = onArticleClick,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsTopBar(
    state: NewsState,
    onAction: (NewsAction) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                Text(
                    text = stringResource(id = R.string.news_app_title),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        )

        SearchField(
            query = state.searchQuery,
            onQueryChange = { onAction(NewsAction.OnSearchQueryChange(it)) },
            onClear = { onAction(NewsAction.ClearSearch) }
        )

        FilterChipRow(
            options = NewsLanguage.entries,
            selectedOption = state.selectedLanguage,
            optionLabel = { language -> "${language.flagEmoji} ${language.displayName}" },
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
            onOptionSelected = { onAction(NewsAction.OnLanguageSelect(it)) }
        )

        FilterChipRow(
            options = NewsCategory.entries,
            selectedOption = state.selectedCategory,
            optionLabel = { category -> "${category.emoji} ${stringResource(id = category.titleRes)}" },
            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
            onOptionSelected = { onAction(NewsAction.OnCategorySelect(it)) }
        )
    }
}

@Composable
private fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        placeholder = { Text(stringResource(id = R.string.search_placeholder)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search_content_description)
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClear) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.clear_content_description)
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        )
    )
}

@Composable
private fun NewsContent(
    state: NewsState,
    onAction: (NewsAction) -> Unit,
    onArticleClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val articles = state.displayedArticles

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            (state.isLoading && articles.isEmpty()) || state.isSearchingLoading -> {
                CircularProgressIndicator()
            }

            state.isError && articles.isEmpty() -> {
                ErrorContent(
                    message = state.error?.asString()
                        ?: stringResource(id = R.string.error_failed_to_load_news),
                    onRetry = { onAction(NewsAction.Refresh) }
                )
            }

            state.isSearchActive && articles.isEmpty() -> {
                Text(
                    text = stringResource(id = R.string.error_no_news_matching_search),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            articles.isNotEmpty() -> {
                ArticleList(
                    articles = articles,
                    showLoadingFooter = state.isLoading && !state.isSearchActive,
                    onLoadMore = { onAction(NewsAction.Paginate) },
                    onArticleClick = onArticleClick
                )
            }
        }
    }
}

@Composable
private fun ArticleList(
    articles: List<Article>,
    showLoadingFooter: Boolean,
    onLoadMore: () -> Unit,
    onArticleClick: (String) -> Unit
) {
    val listState = rememberLazyListState()

    PaginationEffect(
        listState = listState,
        itemCount = articles.size,
        onLoadMore = onLoadMore
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
        state = listState
    ) {
        // articleId unikaldır (ViewModel-də distinctBy, bazada PrimaryKey) – key kimi təhlükəsizdir
        items(items = articles, key = { it.articleId }) { article ->
            ArticleItem(
                article = article,
                onArticleClick = onArticleClick
            )
        }

        if (showLoadingFooter) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
