package com.swarn.redditviewer.data


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("after")
    var after: String,
    @SerializedName("before")
    var before: Any,
    @SerializedName("children")
    var children: List<Children>,
    @SerializedName("dist")
    var dist: Int,
    @SerializedName("modhash")
    var modhash: String
)