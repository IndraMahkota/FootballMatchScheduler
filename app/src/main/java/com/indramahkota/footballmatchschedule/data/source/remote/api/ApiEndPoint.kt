package com.indramahkota.footballmatchschedule.data.source.remote.api

import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.TeamDetailsApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndPoint {
    @GET("lookupleague.php")
    fun getLeagueDetailsByLeagueId(
        @Query("id") leagueId : String
    ) : Call<LeagueDetailsApiResponse>

    @GET("lookupteam.php")
    fun getTeamDetailById(
        @Query("id") matchId : String
    ) : Call<TeamDetailsApiResponse>

    @GET("lookupevent.php")
    fun getMatchDetailById(
        @Query("id") matchId : String
    ) : Call<MatchDetailsApiResponse>

    @GET("lookup_all_teams.php")
    suspend fun getAllTeamByLeagueId(
        @Query("id") leagueId : String
    ) : TeamDetailsApiResponse

    @GET("eventsnextleague.php")
    suspend fun getNextMatchesByLeagueId(
        @Query("id") leagueId : String
    ) : MatchDetailsApiResponse

    @GET("eventspastleague.php")
    suspend fun getLastMatchesByLeagueId(
        @Query("id") leagueId : String
    ) : MatchDetailsApiResponse
}