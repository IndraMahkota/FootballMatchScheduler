package com.indramahkota.footballapp.data.source.remote.api

import com.indramahkota.footballapp.data.source.remote.apiresponse.ClassementApiResponse
import com.indramahkota.footballapp.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballapp.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballapp.data.source.remote.apiresponse.TeamDetailsApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndPoint {
    @GET("lookupleague.php")
    suspend fun getLeagueDetailsByLeagueId(
        @Query("id") leagueId : String
    ) : LeagueDetailsApiResponse

    @GET("lookupteam.php")
    suspend fun getTeamDetailsById(
        @Query("id") matchId : String
    ) : TeamDetailsApiResponse

    @GET("lookupevent.php")
    suspend fun getMatchDetailsById(
        @Query("id") matchId : String
    ) : MatchDetailsApiResponse

    @GET("lookup_all_teams.php")
    suspend fun getAllTeamByLeagueId(
        @Query("id") leagueId : String
    ) : TeamDetailsApiResponse

    @GET("eventsnextleague.php")
    suspend fun getNextMatchByLeagueId(
        @Query("id") leagueId : String
    ) : MatchDetailsApiResponse

    @GET("eventspastleague.php")
    suspend fun getLastMatchByLeagueId(
        @Query("id") leagueId : String
    ) : MatchDetailsApiResponse

    @GET("lookuptable.php")
    suspend fun getClassementTable(
        @Query("l") leagueId : String
    ) : ClassementApiResponse
}