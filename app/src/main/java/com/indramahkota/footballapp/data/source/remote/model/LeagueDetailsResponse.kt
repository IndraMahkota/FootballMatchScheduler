package com.indramahkota.footballapp.data.source.remote.model

import com.google.gson.annotations.SerializedName
import com.indramahkota.footballapp.data.source.remote.model.LeagueDetailsModel

data class LeagueDetailsResponse(
    @SerializedName("leagues")
    val leagues: List<LeagueDetailsModel>? = null
)