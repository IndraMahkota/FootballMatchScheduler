package com.indramahkota.footballmatchschedule.data.source.remote.apiresponse

import com.google.gson.annotations.SerializedName
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.LeagueDetailsApiModel

data class LeagueDetailsApiResponse(
    @SerializedName("leagues")
    val leagues: List<LeagueDetailsApiModel>? = null
)