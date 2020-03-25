package com.indramahkota.footballapp.data.source

import com.indramahkota.footballapp.data.source.locale.database.AppDao
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity
import com.indramahkota.footballapp.data.source.remote.EndPointService
import com.indramahkota.footballapp.data.source.remote.model.ClassementResponse
import com.indramahkota.footballapp.data.source.remote.model.LeagueDetailsResponse
import com.indramahkota.footballapp.data.source.remote.model.MatchDetailsResponse
import com.indramahkota.footballapp.data.source.remote.model.TeamDetailsResponse

class FakeFootballAppRepository constructor(private val service: EndPointService,
                                            private val dao: AppDao ) {
    suspend fun loadLeagueDetailsByLeagueId(id: String): Resource<LeagueDetailsResponse?> {
        return try {
            Resource.success(service.getLeagueDetailsByLeagueId(id))
        } catch(e: Exception) {
            Resource.error(e.message,
                LeagueDetailsResponse()
            )
        }
    }

    suspend fun loadMatchDetailById(id: String): Resource<MatchDetailsResponse?> {
        return try {
            Resource.success(service.getMatchDetailsById(id))
        } catch(e: Exception) {
            Resource.error(e.message,
                MatchDetailsResponse()
            )
        }
    }

    suspend fun loadTeamDetailById(id: String): Resource<TeamDetailsResponse?> {
        return try {
            Resource.success(service.getTeamDetailsById(id))
        } catch(e: Exception) {
            Resource.error(e.message,
                TeamDetailsResponse()
            )
        }
    }

    suspend fun loadAllTeamByLeagueId(id: String): Resource<TeamDetailsResponse?> {
        return try {
            Resource.success(service.getAllTeamByLeagueId(id))
        } catch (e: Exception) {
            Resource.error(e.message,
                TeamDetailsResponse()
            )
        }
    }

    suspend fun loadNextMatchesByLeagueId(id: String): Resource<MatchDetailsResponse?> {
        return try {
            Resource.success(service.getNextMatchByLeagueId(id))
        } catch (e: Exception) {
            Resource.error(e.message,
                MatchDetailsResponse()
            )
        }
    }

    suspend fun loadLastMatchesByLeagueId(id: String): Resource<MatchDetailsResponse?> {
        return try {
            Resource.success(service.getLastMatchByLeagueId(id))
        } catch (e: Exception) {
            Resource.error(e.message,
                MatchDetailsResponse()
            )
        }
    }

    suspend fun loadClassementByLeagueId(id: String): Resource<ClassementResponse?> {
        return try {
            Resource.success(service.getClassementTable(id))
        } catch (e: Exception) {
            Resource.error(e.message,
                ClassementResponse()
            )
        }
    }

    suspend fun loadAllFavoriteMatch(): List<MatchEntity> {
        return dao.getAllFavoriteMatch()
    }

    suspend fun loadFavoriteMatchById(id: String): MatchEntity {
        return dao.getFavoriteMatchById(id)
    }

    suspend fun insertFavoriteMatchById(match: MatchEntity) {
        return dao.insertFavoriteMatch(match)
    }

    suspend fun deleteFavoriteMatchById(match: MatchEntity) {
        return dao.deleteFavoriteMatch(match)
    }

    suspend fun updateFavoriteMatchById(match: MatchEntity) {
        return dao.updateFavoriteMatch(match)
    }

    suspend fun loadAllFavoriteTeam(): List<TeamEntity> {
        return dao.getAllFavoriteTeam()
    }

    suspend fun loadFavoriteTeamById(id: String): TeamEntity {
        return dao.getFavoriteTeamById(id)
    }

    suspend fun insertFavoriteTeamById(team: TeamEntity) {
        return dao.insertFavoriteTeam(team)
    }

    suspend fun deleteFavoriteTeamById(team: TeamEntity) {
        return dao.deleteFavoriteTeam(team)
    }

    suspend fun updateFavoriteTeamById(team: TeamEntity) {
        return dao.updateFavoriteTeam(team)
    }
}