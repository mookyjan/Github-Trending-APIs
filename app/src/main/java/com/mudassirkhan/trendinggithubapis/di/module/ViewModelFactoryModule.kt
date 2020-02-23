package com.mudassirkhan.githubtrendingapis.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mudassirkhan.trendinggithubapis.ui.TrendingRepositoriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory):
            ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TrendingRepositoriesViewModel::class)
    internal abstract fun provideTrendingRepositoriesListViewModel(viewModel: TrendingRepositoriesViewModel): ViewModel
}