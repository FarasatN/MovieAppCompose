package com.farasatnovruzov.newsappoffline.article.di

import com.farasatnovruzov.newsappoffline.article.presentation.ArticleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val articleModule = module {
    viewModel { ArticleViewModel(get()) }
}