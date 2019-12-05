package com.indramahkota.footballmatchschedule.data.source.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeagueModel(
    val idLeague: String,
    val strLeague: String,
    val imgLeague: String
) : Parcelable