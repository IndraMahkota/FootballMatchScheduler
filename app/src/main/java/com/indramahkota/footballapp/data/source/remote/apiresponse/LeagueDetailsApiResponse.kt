package com.indramahkota.footballapp.data.source.remote.apiresponse

import com.google.gson.annotations.SerializedName
import com.indramahkota.footballapp.data.source.remote.apimodel.LeagueDetailsApiModel

data class LeagueDetailsApiResponse(
    @SerializedName("leagues")
    val leagues: List<LeagueDetailsApiModel>? = null
)