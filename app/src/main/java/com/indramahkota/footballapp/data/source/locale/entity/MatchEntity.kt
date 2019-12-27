package com.indramahkota.footballapp.data.source.locale.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity.Column.AWAY_IMAGE
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity.Column.AWAY_SCORE
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity.Column.AWAY_TEAM
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity.Column.DATE_EVENT
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity.Column.HOME_IMAGE
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity.Column.HOME_SCORE
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity.Column.HOME_TEAM
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity.Column.ID_AWAY_TEAM
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity.Column.ID_EVENT
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity.Column.ID_HOME_TEAM
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity.Companion.TABLE_MATCH
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_MATCH)
data class MatchEntity(
    @PrimaryKey
    @ColumnInfo(name = ID_EVENT)
    val idEvent: String,

    @ColumnInfo(name = ID_HOME_TEAM)
    val idHomeTeam: String,

    @ColumnInfo(name = ID_AWAY_TEAM)
    val idAwayTeam: String,

    @ColumnInfo(name = DATE_EVENT)
    val dateEvent: String,

    @ColumnInfo(name = HOME_TEAM)
    val strHomeTeam: String,

    @ColumnInfo(name = AWAY_TEAM)
    val strAwayTeam: String,

    @ColumnInfo(name = HOME_SCORE)
    val intHomeScore: String,

    @ColumnInfo(name = AWAY_SCORE)
    val intAwayScore: String,

    @ColumnInfo(name = HOME_IMAGE)
    val sourceHomeImage: String,

    @ColumnInfo(name = AWAY_IMAGE)
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
        const val TABLE_MATCH: String = "table_favorite_match"
    }
}