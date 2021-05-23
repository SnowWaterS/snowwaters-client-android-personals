package com.har.onecommitaday.api

object ApiFactory {

    private const val SCHEME: String = "https"
    private const val BASE_HOST: String = "api.github.com"

    val baseUrl: String
        get() = "$SCHEME://$BASE_HOST"

    fun retrofit() = RetrofitFactory.retrofit(baseUrl)
}