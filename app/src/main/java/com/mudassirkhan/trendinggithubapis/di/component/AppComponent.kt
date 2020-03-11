package com.mudassirkhan.trendinggithubapis.di.component

import android.app.Application
import android.content.Context
import com.mudassirkhan.data.NetworkModule

import com.mudassirkhan.trendinggithubapis.GitHubTrendingApp
import com.mudassirkhan.trendinggithubapis.di.module.*
import com.mudassirkhan.trendinggithubapis.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(
    AppModule::class,
    AndroidInjectionModule::class,
    ViewModelFactoryModule::class,
    ActivityModule::class,
    NetworkModule::class,
    DataModule::class,
    DomainModule::class

))
interface AppComponent {

    fun inject(application: GitHubTrendingApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}