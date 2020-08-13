package com.swarn.redditviewer.data


import com.google.gson.annotations.SerializedName

data class MediaEmbedX(
    @SerializedName("content")
    var content: String,
    @SerializedName("height")
    var height: Int,
    @SerializedName("scrolling")
    var scrolling: Boolean,
    @SerializedName("width")
    var width: Int
)