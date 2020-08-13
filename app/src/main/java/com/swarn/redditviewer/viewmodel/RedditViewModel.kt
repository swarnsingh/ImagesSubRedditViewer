package com.swarn.redditviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swarn.redditviewer.data.Reddit
import com.swarn.redditviewer.repository.RedditRepository
import com.swarn.redditviewer.vo.Resource

class RedditViewModel : ViewModel() {

    fun getRedditImages(): MutableLiveData<Resource<Reddit>>? {
        return RedditRepository.getRedditImages()
    }
}