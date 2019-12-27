package com.indramahkota.footballapp.data.source

import com.indramahkota.footballapp.data.source.locale.database.AppDao
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity
import com.indramahkota.footballapp.data.source.remote.api.ApiEndPoint
import com.indramahkota.footballapp.data.source.remote.apiresponse.ClassementApiResponse
import com.indramahkota.footballapp.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballapp.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballapp.data.source.remote.apiresponse.TeamDetailsApiResponse

class FakeFootballAppRepository constructor(private val api: ApiEndPoint,
                                                private val dao: AppDao ) : FootballAppDataSource {
    override suspend fun loadLeagueDetailsByLeagueId(id: String): Resource<LeagueDetailsApiResponse?> {
        return try {
            Resource.success(api.getLeagueDetailsByLeagueId(id))
        } catch(e: Exception) {
            Resource.error(e.message, LeagueDetailsApiResponse())
        }
    }

    override suspend fun loadMatchDetailById(id: String): Resource<MatchDetailsApiResponse?> {
        return try {
            Resource.success(api.getMatchDetailsById(id))
        } catch(e: Exception) {
            Resource.error(e.message, MatchDetailsApiResponse())
        }
    }

    override suspend fun loadTeamDetailById(id: String): Resource<TeamDetailsApiResponse?> {
        return try {
            Resource.success(api.getTeamDetailsById(id))
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
            Resource.success(api.getNextMatchByLeagueId(id))
        } catch (e: Exception) {
            Resource.error(e.message, MatchDetailsApiResponse())
        }
    }

    override suspend fun loadLastMatchesByLeagueId(id: String): Resource<MatchDetailsApiResponse?> {
        return try {
            Resource.success(api.getLastMatchByLeagueId(id))
        } catch (e: Exception) {
            Resource.error(e.message, MatchDetailsApiResponse())
        }
    }

    override suspend fun loadClassementByLeagueId(id: String): Resource<ClassementApiResponse?> {
        return try {
            Resource.success(api.getClassementTable(id))
        } catch (e: Exception) {
            Resource.error(e.message, ClassementApiResponse())
        }
    }

    override suspend fun loadAllFavoriteMatch(): List<MatchEntity> {
        return dao.getAllFavoriteMatch()
    }

    override suspend fun loadFavoriteMatchById(id: String): MatchEntity {
        return dao.getFavoriteMatchById(id)
    }

    override suspend fun insertFavoriteMatchById(match: MatchEntity) {
        return dao.insertFavoriteMatch(match)
    }

    override suspend fun deleteFavoriteMatchById(match: MatchEntity) {
        return dao.deleteFavoriteMatch(match)
    }

    override suspend fun updateFavoriteMatchById(match: MatchEntity) {
        return dao.updateFavoriteMatch(match)
    }

    override suspend fun loadAllFavoriteTeam(): List<TeamEntity> {
        return dao.getAllFavoriteTeam()
    }

    override suspend fun loadFavoriteTeamById(id: String): TeamEntity {
        return dao.getFavoriteTeamById(id)
    }

    override suspend fun insertFavoriteTeamById(team: TeamEntity) {
        return dao.insertFavoriteTeam(team)
    }

    override suspend fun deleteFavoriteTeamById(team: TeamEntity) {
        return dao.deleteFavoriteTeam(team)
    }

    override suspend fun updateFavoriteTeamById(team: TeamEntity) {
        return dao.updateFavoriteTeam(team)
    }
}