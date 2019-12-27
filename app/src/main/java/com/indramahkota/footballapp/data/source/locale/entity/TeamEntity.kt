package com.indramahkota.footballapp.data.source.locale.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity.Column.ID_TEAM
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity.Column.TEAM_BADGE
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity.Column.TEAM_DESCRIPTION
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity.Column.TEAM_NAME
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity.Companion.TABLE_TEAM
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_TEAM)
data class TeamEntity(
    @PrimaryKey
    @ColumnInfo(name = ID_TEAM)
    val idTeam: String,

    @ColumnInfo(name = TEAM_NAME)
    val strTeam: String,

    @ColumnInfo(name = TEAM_BADGE)
    val strTeamBadge: String,

    @ColumnInfo(name = TEAM_DESCRIPTION)
    val strDescription: String
) : Parcelable {

    object Column {
        const val ID_TEAM: String = "id_team"
        const val TEAM_NAME: String = "team_name"
        const val TEAM_BADGE: String = "team_badge"
        const val TEAM_DESCRIPTION: String = "team_description"
    }

    companion object {
        const val TABLE_TEAM: String = "table_favorite_team"
    }
}