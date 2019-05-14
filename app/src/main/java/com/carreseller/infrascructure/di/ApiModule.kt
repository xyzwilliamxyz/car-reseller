package com.carreseller.infrascructure.di

import com.carreseller.BuildConfig
import com.carreseller.gateways.remote.CarTypesApi
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

private val retrofit = provideRetrofitInterface()

val apiModule = module {
    single { retrofit.create(CarTypesApi::class.java) }
}

private fun provideRetrofitInterface(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_API_URL)
        .client(requestInterceptor())
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()
}

/**
 * Adds interceptor to request in order to set the api key
 */
private fun requestInterceptor(): OkHttpClient {
    return OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request()
        val url = request.url().newBuilder().addQueryParameter("wa_key", BuildConfig.API_KEY).build()
        chain.proceed(request.newBuilder().url(url).build())
    }.build()
}
