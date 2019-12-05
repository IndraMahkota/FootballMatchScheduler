package com.indramahkota.footballmatchschedule.data.source.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchModel(
    val idEvent: String,
    val idHomeTeam: String,
    val idAwayTeam: String,
    val dateEvent: String,
    val strHomeTeam: String,
    val strAwayTeam: String,
    val intHomeScore: String,
    val intAwayScore: String,
    val sourceHomeImage: String,
    val sourceAwayImage: String
) : Parcelable