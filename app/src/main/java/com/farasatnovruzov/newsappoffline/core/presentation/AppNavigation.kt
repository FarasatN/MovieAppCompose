package com.farasatnovruzov.newsappoffline.core.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.farasatnovruzov.newsappoffline.article.presentation.ArticleScreenCore
import com.farasatnovruzov.newsappoffline.news.presentation.NewsScreenCore

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.News
    ) {
        composable<Screen.News> {
            NewsScreenCore(
                onArticleClick = { articleId -> navController.navigate(Screen.Article(articleId)) }
            )
        }
        composable<Screen.Article> { backStackEntry ->
            val route: Screen.Article = backStackEntry.toRoute()
            ArticleScreenCore(articleId = route.articleId)
        }
    }
}
