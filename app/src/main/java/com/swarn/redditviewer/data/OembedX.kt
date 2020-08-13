package com.swarn.redditviewer.data


import com.google.gson.annotations.SerializedName

data class OembedX(
    @SerializedName("description")
    var description: String,
    @SerializedName("height")
    var height: Int,
    @SerializedName("html")
    var html: String,
    @SerializedName("provider_name")
    var providerName: String,
    @SerializedName("provider_url")
    var providerUrl: String,
    @SerializedName("thumbnail_height")
    var thumbnailHeight: Int,
    @SerializedName("thumbnail_url")
    var thumbnailUrl: String,
    @SerializedName("thumbnail_width")
    var thumbnailWidth: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("version")
    var version: String,
    @SerializedName("width")
    var width: Int
)