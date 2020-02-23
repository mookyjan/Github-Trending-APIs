package com.mudassirkhan.domain

import io.reactivex.Single

abstract class UseCase<in Params,T> protected constructor(private val schedulers:Schedulers) {

    protected abstract fun buildUseCaseObservable(params: Params?): Single<T>

    fun execute(params: Params? =null) : Single<T> {
        val observable = this.buildUseCaseObservable(params)
            .subscribeOn(schedulers.subscribeOn)
            .observeOn(schedulers.observeOn)
        return observable

    }

}