package com.mudassirkhan.domain.utils

import com.mudassirkhan.domain.Schedulers
import io.reactivex.Scheduler

class TestSchedulers : Schedulers {

    override val observeOn: Scheduler
        get() = io.reactivex.schedulers.Schedulers.trampoline()

    override val subscribeOn: Scheduler
        get() = io.reactivex.schedulers.Schedulers.trampoline()
}