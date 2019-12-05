package com.indramahkota.footballmatchschedule.data.source.remote.apiresponse

import com.google.gson.annotations.SerializedName
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.MatchDetailsApiModel

data class SearchMatchsApiResponse(
    @SerializedName("event")
    val event: List<MatchDetailsApiModel>? = null
)