package com.indramahkota.footballmatchschedule.data.source.remote.apiresponse

import com.google.gson.annotations.SerializedName
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.MatchDetailsApiModel

data class MatchDetailsApiResponse(
    @SerializedName("events")
    val events: List<MatchDetailsApiModel>? = null
)