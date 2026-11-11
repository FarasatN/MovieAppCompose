package com.farasatnovruzov.newsappoffline.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.farasatnovruzov.movieappcompose.R
import com.farasatnovruzov.newsappoffline.core.domain.NewsError

/** Domain xətasını istifadəçiyə göstəriləcək (tərcümə olunan) mətnə çevirir. */
@Composable
fun NewsError.asString(): String = stringResource(
    id = when (this) {
        NewsError.NoData -> R.string.no_data_available
        NewsError.PaginationFailed -> R.string.pagination_error
        NewsError.SearchFailed -> R.string.search_failed
        NewsError.ArticleNotFound -> R.string.article_not_found
        NewsError.ArticleLoadFailed -> R.string.failed_to_load_article
        NewsError.InvalidArticleId -> R.string.invalid_article_id
    }
)
