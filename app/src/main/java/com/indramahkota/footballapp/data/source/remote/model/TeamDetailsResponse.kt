package com.indramahkota.footballapp.data.source.remote.model

import com.google.gson.annotations.SerializedName
import com.indramahkota.footballapp.data.source.remote.model.TeamDetailsiModel

data class TeamDetailsResponse(
    @SerializedName("teams")
    val teams: List<TeamDetailsiModel>? = null
)