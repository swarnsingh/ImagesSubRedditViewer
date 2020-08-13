package com.swarn.redditviewer.data


import com.google.gson.annotations.SerializedName

data class Media(
    @SerializedName("oembed")
    var oembed: Oembed,
    @SerializedName("reddit_video")
    var redditVideo: RedditVideo,
    @SerializedName("type")
    var type: String
)