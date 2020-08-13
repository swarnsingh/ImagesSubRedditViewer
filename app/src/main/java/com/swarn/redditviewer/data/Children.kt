package com.swarn.redditviewer.data


import com.google.gson.annotations.SerializedName

data class Children(
    @SerializedName("data")
    var `data`: DataX,
    @SerializedName("kind")
    var kind: String
)