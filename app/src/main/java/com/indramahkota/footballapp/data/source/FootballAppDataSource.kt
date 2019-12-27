package com.indramahkota.footballapp.data.source

import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity
import com.indramahkota.footballapp.data.source.remote.apiresponse.ClassementApiResponse
import com.indramahkota.footballapp.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballapp.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballapp.data.source.remote.apiresponse.TeamDetailsApiResponse

interface FootballAppDataSource {
    suspend fun loadLeagueDetailsByLeagueId(id: String): Resource<LeagueDetailsApiResponse?>
    suspend fun loadMatchDetailById(id: String): Resource<MatchDetailsApiResponse?>
    suspend fun loadTeamDetailById(id: String): Resource<TeamDetailsApiResponse?>
    suspend fun loadAllTeamByLeagueId(id: String): Resource<TeamDetailsApiResponse?>
    suspend fun loadNextMatchesByLeagueId(id: String): Resource<MatchDetailsApiResponse?>
    suspend fun loadLastMatchesByLeagueId(id: String): Resource<MatchDetailsApiResponse?>
    suspend fun loadClassementByLeagueId(id: String): Resource<ClassementApiResponse?>

    suspend fun loadAllFavoriteMatch(): List<MatchEntity>
    suspend fun loadFavoriteMatchById(id: String): MatchEntity
    suspend fun insertFavoriteMatchById(match: MatchEntity)
    suspend fun deleteFavoriteMatchById(match: MatchEntity)
    suspend fun updateFavoriteMatchById(match: MatchEntity)

    suspend fun loadAllFavoriteTeam(): List<TeamEntity>
    suspend fun loadFavoriteTeamById(id: String): TeamEntity
    suspend fun insertFavoriteTeamById(team: TeamEntity)
    suspend fun deleteFavoriteTeamById(team: TeamEntity)
    suspend fun updateFavoriteTeamById(team: TeamEntity)
}