package com.indramahkota.footballmatchschedule.data.source

import androidx.lifecycle.LiveData
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.TeamDetailsApiResponse

interface FLeagueDataSource {
    fun loadLeagueDetailsByLeagueId(id: String): LiveData<Resource<LeagueDetailsApiResponse?>>
    fun loadMatchDetailById(id: String): LiveData<Resource<MatchDetailsApiResponse?>>
    fun loadTeamDetailById(id: String): LiveData<Resource<TeamDetailsApiResponse?>>

    suspend fun loadAllTeamByLeagueId(id: String): Resource<TeamDetailsApiResponse?>
    suspend fun loadNextMatchesByLeagueId(id: String): Resource<MatchDetailsApiResponse?>
    suspend fun loadLastMatchesByLeagueId(id: String): Resource<MatchDetailsApiResponse?>
}