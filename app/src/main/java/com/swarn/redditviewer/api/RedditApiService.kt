package com.swarn.redditviewer.api

import com.swarn.redditviewer.data.Reddit
import retrofit2.Call
import retrofit2.http.GET

/**
 * @author Swarn Singh
 */
interface RedditApiService {
    @GET("hot.json")
    fun getRedditImages(): Call<Reddit>
}