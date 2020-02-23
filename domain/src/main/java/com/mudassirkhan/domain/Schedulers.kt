package com.mudassirkhan.domain

import io.reactivex.Scheduler

interface Schedulers {
    val subscribeOn: Scheduler
    val observeOn: Scheduler
}