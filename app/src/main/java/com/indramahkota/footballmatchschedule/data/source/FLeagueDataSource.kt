package com.indramahkota.footballmatchschedule.data.source

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
}