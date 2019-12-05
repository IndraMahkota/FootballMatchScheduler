package com.indramahkota.footballmatchschedule.data.source

import androidx.lifecycle.LiveData
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.TeamDetailsApiResponse

interface FLeagueDataSource {
    fun loadLeagueDetailsByLeagueId(id: String): LiveData<Resource<LeagueDetailsApiResponse?>>
    fun loadNextMatchesByLeagueId(id: String): LiveData<Resource<MatchDetailsApiResponse?>>
    fun loadLastMatchesByLeagueId(id: String): LiveData<Resource<MatchDetailsApiResponse?>>
    fun loadMatchDetailById(id: String): LiveData<Resource<MatchDetailsApiResponse?>>
    fun loadTeamDetailById(id: String): LiveData<Resource<TeamDetailsApiResponse?>>
    fun loadAllTeamByLeagueId(id: String): LiveData<Resource<TeamDetailsApiResponse?>>
}