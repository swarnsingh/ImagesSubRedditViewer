package com.swarn.redditviewer.data


import com.google.gson.annotations.SerializedName

data class ImageX(
    @SerializedName("id")
    var id: String,
    @SerializedName("resolutions")
    var resolutions: List<ResolutionX>,
    @SerializedName("source")
    var source: SourceX,
    @SerializedName("variants")
    var variants: VariantsX
)