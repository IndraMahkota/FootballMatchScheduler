package com.indramahkota.footballapp.data.source.remote

import com.indramahkota.footballapp.data.source.remote.model.ClassementResponse
import com.indramahkota.footballapp.data.source.remote.model.LeagueDetailsResponse
import com.indramahkota.footballapp.data.source.remote.model.MatchDetailsResponse
import com.indramahkota.footballapp.data.source.remote.model.TeamDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EndPointService {
    @GET("lookupleague.php")
    suspend fun getLeagueDetailsByLeagueId(
        @Query("id") leagueId: String
    ): LeagueDetailsResponse

    @GET("lookupteam.php")
    suspend fun getTeamDetailsById(
        @Query("id") matchId: String
    ): TeamDetailsResponse

    @GET("lookupevent.php")
    suspend fun getMatchDetailsById(
        @Query("id") matchId: String
    ): MatchDetailsResponse

    @GET("lookup_all_teams.php")
    suspend fun getAllTeamByLeagueId(
        @Query("id") leagueId: String
    ): TeamDetailsResponse

    @GET("eventsnextleague.php")
    suspend fun getNextMatchByLeagueId(
        @Query("id") leagueId: String
    ): MatchDetailsResponse

    @GET("eventspastleague.php")
    suspend fun getLastMatchByLeagueId(
        @Query("id") leagueId: String
    ): MatchDetailsResponse

    @GET("lookuptable.php")
    suspend fun getClassementTable(
        @Query("l") leagueId: String
    ): ClassementResponse
}