package com.indramahkota.footballapp.data.source.locale.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamEntity(
    val idTeam: String,
    val strTeam: String,
    val strTeamBadge: String,
    val strDescription: String
) : Parcelable {

    object Column {
        const val ID_TEAM: String = "id_team"
        const val TEAM_NAME: String = "team_name"
        const val TEAM_BADGE: String = "team_badge"
        const val TEAM_DESCRIPTION: String = "team_description"
    }

    companion object {
        const val TABLE_NAME: String = "table_favorite_team"
    }
}