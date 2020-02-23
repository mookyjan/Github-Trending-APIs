package com.mudassirkhan.trendinggithubapis.ui

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.Timber
import com.mudassirkhan.domain.entity.GithubTrendingEntity
import com.mudassirkhan.domain.usecase.GetGithubTrendingApiUseCase
import com.mudassirkhan.githubtrendingapis.ui.model.TrendRepositoryModel
import com.mudassirkhan.githubtrendingapis.ui.model.mapToModel
import com.mudassirkhan.githubtrendingapis.utils.IPreference
import com.mudassirkhan.githubtrendingapis.utils.IResourceProvider
import com.mudassirkhan.githubtrendingapis.utils.isNetworkConnected
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import java.time.Duration
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit

import javax.inject.Inject

class TrendingRepositoriesViewModel @Inject constructor(private val getGithubTrendingApiUseCase: GetGithubTrendingApiUseCase,
                                                        private val iResourceProvider: IResourceProvider,
                                                        private val iPreference: IPreference) : ViewModel() {

    //variable for the news list
    var repositoriesList = ObservableArrayList<TrendRepositoryModel>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    //variable for loading progress
    val loading = ObservableBoolean()
    //variable for error message
    val error = ObservableField<String>()
    val empty = MutableLiveData(false)

    fun refresh() = loadTrendingRepositories(true)

     fun loadTrendingRepositories(refresh: Boolean=false){
        loading.set(true)
         empty.value=false
        getGithubTrendingApiUseCase.execute(refresh)
            .subscribeBy(onSuccess ={ t->
                Timber.d { "trending  api response success ${t.size}" }
                loading.set(false)
                empty.postValue(false)
                repositoriesList.clear()
                repositoriesList.addAll(t.mapToModel())

            },onError ={e->
                Timber.e { "trending api error $e" }
                loading.set(false)
                empty.postValue(true)
                error.set(e.localizedMessage ?: e.message ?: "Unknown error")
            }).addTo(compositeDisposable)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}