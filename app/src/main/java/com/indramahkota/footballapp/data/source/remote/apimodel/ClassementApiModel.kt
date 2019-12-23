package com.indramahkota.footballapp.data.source.remote.apimodel

import com.google.gson.annotations.SerializedName

data class ClassementApiModel(
    @SerializedName("draw")
    val draw: String? = null,
    @SerializedName("goalsagainst")
    val goalsagainst: String? = null,
    @SerializedName("goalsdifference")
    val goalsdifference: String? = null,
    @SerializedName("goalsfor")
    val goalsfor: String? = null,
    @SerializedName("loss")
    val loss: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("played")
    val played: String? = null,
    @SerializedName("teamid")
    val teamid: String? = null,
    @SerializedName("total")
    val total: String? = null,
    @SerializedName("win")
    val win: String? = null
)