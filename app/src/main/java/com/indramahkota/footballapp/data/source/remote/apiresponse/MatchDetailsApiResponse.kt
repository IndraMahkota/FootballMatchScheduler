package com.indramahkota.footballapp.data.source.remote.apiresponse

import com.google.gson.annotations.SerializedName
import com.indramahkota.footballapp.data.source.remote.apimodel.MatchDetailsApiModel

data class MatchDetailsApiResponse(
    @SerializedName("events")
    val events: List<MatchDetailsApiModel>? = null
)