package com.indramahkota.footballapp.data.source.remote.model

import com.google.gson.annotations.SerializedName
import com.indramahkota.footballapp.data.source.remote.model.MatchDetailsModel

data class MatchDetailsResponse(
    @SerializedName("events")
    val events: List<MatchDetailsModel>? = null
)