package com.swarn.redditviewer.data


import com.google.gson.annotations.SerializedName

data class Reddit(
    @SerializedName("data")
    var `data`: Data,
    @SerializedName("kind")
    var kind: String,

    var redditImageUrls: List<String>
)