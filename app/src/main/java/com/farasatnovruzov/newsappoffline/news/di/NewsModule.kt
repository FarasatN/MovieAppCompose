package com.farasatnovruzov.newsappoffline.news.di

import com.farasatnovruzov.newsappoffline.news.presentation.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newsModule = module {
    viewModel { NewsViewModel(get()) }
}
