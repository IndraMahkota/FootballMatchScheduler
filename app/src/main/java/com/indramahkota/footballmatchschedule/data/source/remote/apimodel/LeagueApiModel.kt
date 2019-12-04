package com.indramahkota.footballmatchschedule.data.source.remote.apimodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeagueApiModel(
    val idLeague: String,
    val strLeague: String,
    val imgLeague: String
) : Parcelable