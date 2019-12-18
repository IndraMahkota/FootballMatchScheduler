package com.indramahkota.footballapp.data.source.remote.apiresponse

import com.google.gson.annotations.SerializedName
import com.indramahkota.footballapp.data.source.remote.apimodel.TeamDetailsApiModel

data class TeamDetailsApiResponse(
    @SerializedName("teams")
    val teams: List<TeamDetailsApiModel>? = null
)