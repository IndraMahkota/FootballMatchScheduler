package com.indramahkota.footballmatchschedule.data.source.locale.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchEntity(
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
) : Parcelable {

    object Column {
        const val ID_EVENT: String = "id_event"
        const val ID_HOME_TEAM: String = "id_home_team"
        const val ID_AWAY_TEAM: String = "id_away_team"
        const val DATE_EVENT: String = "date_event"
        const val HOME_TEAM: String = "home_team"
        const val AWAY_TEAM: String = "away_team"
        const val HOME_SCORE: String = "home_score"
        const val AWAY_SCORE: String = "away_score"
        const val HOME_IMAGE: String = "home_image"
        const val AWAY_IMAGE: String = "away_image"
    }

    companion object {
        const val TABLE_NAME: String = "table_favorite_match"
    }
}