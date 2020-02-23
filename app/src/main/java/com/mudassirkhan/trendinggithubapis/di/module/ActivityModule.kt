package com.mudassirkhan.trendinggithubapis.di.module

import com.mudassirkhan.trendinggithubapis.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity
}