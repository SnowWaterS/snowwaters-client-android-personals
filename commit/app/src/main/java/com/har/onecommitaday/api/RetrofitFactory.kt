package com.har.onecommitaday.api

import com.google.gson.GsonBuilder
import com.har.onecommitaday.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    private const val TIMEOUT: Long = 10
    private var interceptors: MutableList<Interceptor> = mutableListOf()
    private var networkInterceptors: MutableList<Interceptor> = mutableListOf()

    fun retrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .client(okhttpClient())
        .baseUrl(baseUrl)
        .addCallAdapterFactory(ApiResponseAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

    private fun okhttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        networkInterceptors.forEach {
            builder.addInterceptor(it)
        }

        interceptors.forEach {
            builder.addInterceptor(it)
        }

        return builder.addInterceptor(loggingInterceptor).build()
    }
}