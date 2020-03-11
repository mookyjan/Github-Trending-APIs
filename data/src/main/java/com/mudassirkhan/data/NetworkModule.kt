package com.mudassirkhan.data

import androidx.annotation.VisibleForTesting
import com.mudassirkhan.data.remote.api.GitHubApiService
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    @VisibleForTesting
     fun provideHackerAPI(okHttpClient: OkHttpClient,baseUrl:String ): GitHubApiService{
//        val baseUrl =  BuildConfig.API_URL
        val moshi = Moshi.Builder()
            .add(Wrapped.ADAPTER_FACTORY)
//            .add(MoshiConverters())
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build().create(GitHubApiService::class.java)

    }

    @Provides
    @Singleton
    @VisibleForTesting
     fun provideOkHttpClient() : OkHttpClient {

        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(getHttpLoggingInterceptor())
        }

        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)
        builder.writeTimeout(60, TimeUnit.SECONDS)

//        builder.addInterceptor(headerInterceptor)

        return builder.build()
    }

    @Provides
    @Singleton
     fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
//        if (Constants.getBuildType() == Constants.TYPE.RELEASE.ordinal) {
//            interceptor.level = HttpLoggingInterceptor.Level.NONE
//        } else {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        }
        return interceptor
    }

    @Provides
    @Singleton
    fun getUrl():String{
        return  BuildConfig.API_URL
    }



}