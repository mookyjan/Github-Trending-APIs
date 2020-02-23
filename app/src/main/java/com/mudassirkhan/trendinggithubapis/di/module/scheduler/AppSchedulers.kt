package com.mudassirkhan.githubtrendingapis.di.module.scheduler


import com.mudassirkhan.domain.Schedulers
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class AppSchedulers : Schedulers {

        override val subscribeOn: Scheduler
        get() = io.reactivex.schedulers.Schedulers.io()

        override val observeOn: Scheduler
        get() = AndroidSchedulers.mainThread()
}