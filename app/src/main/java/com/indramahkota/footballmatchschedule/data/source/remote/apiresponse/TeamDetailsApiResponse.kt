package com.indramahkota.footballmatchschedule.data.source.remote.apiresponse

import com.google.gson.annotations.SerializedName
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.TeamDetailsApiModel

data class TeamDetailsApiResponse(
    @SerializedName("teams")
    val teams: List<TeamDetailsApiModel>? = null
)