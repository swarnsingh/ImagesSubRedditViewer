package com.swarn.redditviewer.api

import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Swarn Singh
 */
object RedditApiClient {
    private const val BASE_URL = "https://www.reddit.com/r/images/"

    private var adapterBuilder: retrofit2.Retrofit.Builder? = null

    private fun createDefaultAdapter() {
        adapterBuilder = retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    fun <S> createService(serviceClass: Class<S>): S {
        if (adapterBuilder == null) {
            createDefaultAdapter()
        }
        return this.adapterBuilder!!.build().create(serviceClass)
    }
}