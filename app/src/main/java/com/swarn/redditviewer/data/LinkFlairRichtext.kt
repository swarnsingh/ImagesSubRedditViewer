package com.swarn.redditviewer.data


import com.google.gson.annotations.SerializedName

data class LinkFlairRichtext(
    @SerializedName("e")
    var e: String,
    @SerializedName("t")
    var t: String
)