package com.mudassirkhan.trendinggithubapis.ui

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.Timber
import com.mudassirkhan.domain.usecase.GetGithubTrendingApiUseCase
import com.mudassirkhan.domain.usecase.GetLastApiCallUseCase
import com.mudassirkhan.trendinggithubapis.R
import com.mudassirkhan.trendinggithubapis.ui.model.TrendRepositoryModel
import com.mudassirkhan.trendinggithubapis.ui.model.mapToModel
import com.mudassirkhan.trendinggithubapis.utils.IResourceProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class TrendingRepositoriesViewModel @Inject constructor(
    private val getGithubTrendingApiUseCase: GetGithubTrendingApiUseCase,
    private val getLastApiCallUseCase: GetLastApiCallUseCase,
    private val iResourceProvider: IResourceProvider
) : ViewModel() {

    //variable for the news list
    var repositoriesList = ObservableArrayList<TrendRepositoryModel>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    //variable for loading progress
    val loading = ObservableBoolean()
    //variable for error message
    val error = ObservableField<String>()
    val empty = MutableLiveData(false)
    var sortedResult: MutableLiveData<List<TrendRepositoryModel>> =
        MutableLiveData(emptyList())

    val lastUpdateValue = MutableLiveData<String>()
    val isLastApiCalled = MutableLiveData<Boolean>(false)

    /*
   *TODO when use init get java.lang.IllegalArgumentException: Parameter specified as non-null is null:
   * for unit test cases
     */
//    init {
//        callAPi()
//    }

    fun refresh() = callAPi(true)

    fun callAPi(isRefresh: Boolean =false){
        loadTrendingRepositories(isRefresh)
        calculateTimeDiff()
    }

    fun sortByNames() {
        val item =
            sortedResult.value!!.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name!! })
        sortedResult.postValue(item)
        repositoriesList.sortedWith(compareBy({ it.name }))
    }

    fun sortByStars() {
        val item = repositoriesList.sortedWith(compareBy({ it.stars }))
        sortedResult.postValue(item)

    }

    /**
     * method to get the list of trending repositories
     */
    fun loadTrendingRepositories(refresh: Boolean = false) {
        loading.set(true)
        empty.value = false
        getGithubTrendingApiUseCase.execute(refresh)
            .subscribeBy(onSuccess = { t ->
                Timber.d { "trending  api response success ${t.size}" }
                loading.set(false)
                empty.postValue(false)
                repositoriesList.clear()
                repositoriesList.addAll(t.mapToModel())
                sortedResult.postValue(t.mapToModel())

            }, onError = { e ->
                Timber.e { "trending api error $e" }
                loading.set(false)
                empty.postValue(true)
//                iResourceProvider.context.getString(R.string.txt_error_title)
                error.set(e.localizedMessage ?: e.message ?: "Unknown error")
            }).addTo(compositeDisposable)
    }



    fun calculateTimeDiff(){
        getLastApiCallUseCase.execute().subscribeBy(
            onSuccess = {
                if (it!=-1L){
                    isLastApiCalled.value=true
                    when(it){
                        in 0..1 ->
                            lastUpdateValue.value=  "0 1o 1 value"
                        in 1..30 ->
                            lastUpdateValue.value=iResourceProvider.context.getString(R.string.txt_time_1_to_30,it)
                        in 30..60 ->
                            lastUpdateValue.value=iResourceProvider.context.getString(R.string.txt_time_30_to_1_hour)
                        in 60..120 ->
                            lastUpdateValue.value=iResourceProvider.context.getString(R.string.txt_time_1_to_2_hour)
                    }
                }else{
                    lastUpdateValue.value="-1 value returned"
                }
            },onError = {
                isLastApiCalled.value=false
            }
        ).addTo(compositeDisposable)

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}