package com.indramahkota.footballapp.data.source.locale.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeagueEntity(
    val idLeague: String,
    val strLeague: String,
    val imgLeague: String
) : Parcelable