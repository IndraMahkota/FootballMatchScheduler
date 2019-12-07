package com.indramahkota.footballmatchschedule.data.source

import com.indramahkota.footballmatchschedule.data.source.remote.api.ApiEndPoint
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.TeamDetailsApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FLeagueRepository @Inject constructor( private val api: ApiEndPoint ) : FLeagueDataSource {
    override suspend fun loadLeagueDetailsByLeagueId(id: String): Resource<LeagueDetailsApiResponse?> {
        return try {
            Resource.success(api.getLeagueDetailsByLeagueId(id))
        } catch(e: Exception) {
            Resource.error(e.message, LeagueDetailsApiResponse())
        }
    }

    override suspend fun loadMatchDetailById(id: String): Resource<MatchDetailsApiResponse?> {
        return try {
            Resource.success(api.getMatchDetailById(id))
        } catch(e: Exception) {
            Resource.error(e.message, MatchDetailsApiResponse())
        }
    }

    override suspend fun loadTeamDetailById(id: String): Resource<TeamDetailsApiResponse?> {
        return try {
            Resource.success(api.getTeamDetailById(id))
        } catch(e: Exception) {
            Resource.error(e.message, TeamDetailsApiResponse())
        }
    }

    override suspend fun loadAllTeamByLeagueId(id: String): Resource<TeamDetailsApiResponse?> {
        return try {
            Resource.success(api.getAllTeamByLeagueId(id))
        } catch (e: Exception) {
            Resource.error(e.message, TeamDetailsApiResponse())
        }
    }

    override suspend fun loadNextMatchesByLeagueId(id: String): Resource<MatchDetailsApiResponse?> {
        return try {
            Resource.success(api.getNextMatchesByLeagueId(id))
        } catch (e: Exception) {
            Resource.error(e.message, MatchDetailsApiResponse())
        }
    }

    override suspend fun loadLastMatchesByLeagueId(id: String): Resource<MatchDetailsApiResponse?> {
        return try {
            Resource.success(api.getLastMatchesByLeagueId(id))
        } catch (e: Exception) {
            Resource.error(e.message, MatchDetailsApiResponse())
        }
    }
}