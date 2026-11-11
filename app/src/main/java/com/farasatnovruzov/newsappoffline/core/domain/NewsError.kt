package com.farasatnovruzov.newsappoffline.core.domain

/**
 * Domain/data qatının xəta modeli.
 * Mətn saxlamır: mətnə çevirmə presentation qatında (stringResource) edilir,
 * beləliklə data qatı Android resurslarından asılı olmur və dil dəyişəndə mətn düzgün qalır.
 */
sealed interface NewsError {
    data object NoData : NewsError
    data object PaginationFailed : NewsError
    data object SearchFailed : NewsError
    data object ArticleNotFound : NewsError
    data object ArticleLoadFailed : NewsError
    data object InvalidArticleId : NewsError
}
