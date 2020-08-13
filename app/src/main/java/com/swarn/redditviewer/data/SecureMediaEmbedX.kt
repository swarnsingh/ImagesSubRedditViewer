package com.swarn.redditviewer.data


import com.google.gson.annotations.SerializedName

data class SecureMediaEmbedX(
    @SerializedName("content")
    var content: String,
    @SerializedName("height")
    var height: Int,
    @SerializedName("media_domain_url")
    var mediaDomainUrl: String,
    @SerializedName("scrolling")
    var scrolling: Boolean,
    @SerializedName("width")
    var width: Int
)