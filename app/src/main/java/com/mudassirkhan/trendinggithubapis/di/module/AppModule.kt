package com.mudassirkhan.githubtrendingapis.di.module

import android.app.Application
import android.content.Context
import com.mudassirkhan.domain.Schedulers
import com.mudassirkhan.githubtrendingapis.di.module.scheduler.AppSchedulers
import com.mudassirkhan.githubtrendingapis.utils.IPreference
import com.mudassirkhan.githubtrendingapis.utils.IResourceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
internal class AppModule {

    @Provides
    @Singleton
    @Named("application.context")
    internal fun providesContext(application: Application): Context {
        return   application.applicationContext
    }

    @Provides
    @Singleton
    internal fun provideSchedulers(): Schedulers = AppSchedulers()

    @Provides
    @Singleton
    internal fun provideResource(context: Context) = IResourceProvider(context)

    @Provides
    @Singleton
    internal fun provideIPreference(context: Context) = IPreference(context)


}