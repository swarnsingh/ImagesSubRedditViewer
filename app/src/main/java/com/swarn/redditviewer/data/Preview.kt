package com.swarn.redditviewer.data


import com.google.gson.annotations.SerializedName

data class Preview(
    @SerializedName("enabled")
    var enabled: Boolean,
    @SerializedName("images")
    var images: List<Image>
)