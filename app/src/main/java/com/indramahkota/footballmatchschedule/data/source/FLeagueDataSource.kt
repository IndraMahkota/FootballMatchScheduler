package com.indramahkota.footballmatchschedule.data.source

import androidx.lifecycle.LiveData
import com.indramahkota.footballmatchschedule.data.source.locale.entity.MatchEntity
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.TeamDetailsApiResponse

interface FLeagueDataSource {
    suspend fun loadLeagueDetailsByLeagueId(id: String): Resource<LeagueDetailsApiResponse?>
    suspend fun loadMatchDetailById(id: String): Resource<MatchDetailsApiResponse?>
    suspend fun loadTeamDetailById(id: String): Resource<TeamDetailsApiResponse?>
    suspend fun loadAllTeamByLeagueId(id: String): Resource<TeamDetailsApiResponse?>
    suspend fun loadNextMatchesByLeagueId(id: String): Resource<MatchDetailsApiResponse?>
    suspend fun loadLastMatchesByLeagueId(id: String): Resource<MatchDetailsApiResponse?>

    fun loadAllFavoriteMatch(): LiveData<List<MatchEntity>>
    fun loadFavoriteMatchById(id: String): LiveData<MatchEntity>
    fun insertFavoriteMatchById(match: MatchEntity)
    fun deleteFavoriteMatchById(match: MatchEntity)
    fun updateFavoriteMatchById(match: MatchEntity)
}