package com.indramahkota.footballapp.data.source.locale.database

import androidx.room.*
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity.Column.ID_EVENT
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity.Companion.TABLE_MATCH
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity.Column.ID_TEAM
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity.Companion.TABLE_TEAM

@Dao
interface AppDao {
    //Match
    @Query("SELECT * from $TABLE_MATCH")
    suspend fun getAllFavoriteMatch(): List<MatchEntity>

    @Query("SELECT * from $TABLE_MATCH WHERE $ID_EVENT = :id")
    suspend fun getFavoriteMatchById(id: String): MatchEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteMatch(match: MatchEntity)

    @Delete
    suspend fun deleteFavoriteMatch(match: MatchEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavoriteMatch(match: MatchEntity)

    //Team
    @Query("SELECT * from $TABLE_TEAM")
    suspend fun getAllFavoriteTeam(): List<TeamEntity>

    @Query("SELECT * from $TABLE_TEAM WHERE $ID_TEAM = :id")
    suspend fun getFavoriteTeamById(id: String): TeamEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteTeam(team: TeamEntity)

    @Delete
    suspend fun deleteFavoriteTeam(team: TeamEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavoriteTeam(team: TeamEntity)
}