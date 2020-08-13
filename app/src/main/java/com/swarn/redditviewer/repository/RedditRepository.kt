package com.swarn.redditviewer.repository

import androidx.lifecycle.MutableLiveData
import com.swarn.redditviewer.api.RedditApiClient
import com.swarn.redditviewer.api.RedditApiService
import com.swarn.redditviewer.data.Reddit
import com.swarn.redditviewer.vo.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Swarn Singh
 */
object RedditRepository {

    private var redditResponse: MutableLiveData<Resource<Reddit>> = MutableLiveData()

    private lateinit var redditApiService: RedditApiService

    fun getRedditImages(): MutableLiveData<Resource<Reddit>> {

        if (redditResponse.value != null) {
            return redditResponse
        }

        redditApiService = RedditApiClient.createService(RedditApiService::class.java)

        redditApiService.getRedditImages()
            .enqueue(object : Callback<Reddit> {
                override fun onFailure(call: Call<Reddit>, t: Throwable) {
                    redditResponse.value = Resource.Error(t.localizedMessage)
                }

                override fun onResponse(call: Call<Reddit>, response: Response<Reddit>) {
                    val res = response.body()
                    if (res != null) {
                        redditResponse.value = Resource.Success(res)
                    } else {
                        redditResponse.value = Resource.Error(response.message())
                    }
                }
            })

        redditResponse.value = Resource.Loading(null)
        return redditResponse
    }
}