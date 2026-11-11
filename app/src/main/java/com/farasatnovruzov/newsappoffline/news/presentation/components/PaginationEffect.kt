package com.farasatnovruzov.newsappoffline.news.presentation.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

private const val NOT_AT_END = -1

/**
 * Siyahının sonuna çatanda [onLoadMore] çağırır.
 *
 * - `rememberUpdatedState` sayəsində həmişə ən son [itemCount] və [onLoadMore] istifadə olunur
 *   (köhnə `remember { derivedStateOf { state... } }` ilk çəkilişin state-ini "dondururdu").
 * - Siyahı ölçüsü açar kimi istifadə olunur: eyni ölçüdə təkrar tetiklənmir, beləliklə xəta zamanı
 *   API-yə sonsuz sorğu dövrü yaranmır. Yeni elementlər gələndə yenidən işləyir.
 */
@Composable
fun PaginationEffect(
    listState: LazyListState,
    itemCount: Int,
    onLoadMore: () -> Unit
) {
    val currentItemCount by rememberUpdatedState(itemCount)
    val currentOnLoadMore by rememberUpdatedState(onLoadMore)

    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisibleIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: NOT_AT_END
            if (currentItemCount > 0 && lastVisibleIndex >= currentItemCount - 1) {
                currentItemCount
            } else {
                NOT_AT_END
            }
        }
            .distinctUntilChanged()
            .filter { it != NOT_AT_END }
            .collect { currentOnLoadMore() }
    }
}
