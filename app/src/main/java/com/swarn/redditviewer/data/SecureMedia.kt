package com.swarn.redditviewer.data


import com.google.gson.annotations.SerializedName

data class SecureMedia(
    @SerializedName("oembed")
    var oembed: OembedX,
    @SerializedName("reddit_video")
    var redditVideo: RedditVideoX,
    @SerializedName("type")
    var type: String
)