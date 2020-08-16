package com.indramahkota.footballapp.data.source.repository

import com.indramahkota.footballapp.data.source.locale.database.AppDao
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity
import com.indramahkota.footballapp.data.source.remote.EndPointService
import com.indramahkota.footballapp.data.source.remote.model.ClassementResponse
import com.indramahkota.footballapp.data.source.remote.model.LeagueDetailsResponse
import com.indramahkota.footballapp.data.source.remote.model.MatchDetailsResponse
import com.indramahkota.footballapp.data.source.remote.model.TeamDetailsResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FootballAppRepository @Inject constructor(
    private val service: EndPointService,
    private val dao: AppDao
) {
    suspend fun loadLeagueDetailsByLeagueId(id: String): Result<LeagueDetailsResponse?> {
        return try {
            Result.Success(
                service.getLeagueDetailsByLeagueId(
                    id
                )
            )
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun loadMatchDetailById(id: String): Result<MatchDetailsResponse?> {
        return try {
            Result.Success(
                service.getMatchDetailsById(
                    id
                )
            )
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun loadTeamDetailById(id: String): Result<TeamDetailsResponse?> {
        return try {
            Result.Success(
                service.getTeamDetailsById(
                    id
                )
            )
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun loadAllTeamByLeagueId(id: String): Result<TeamDetailsResponse?> {
        return try {
            Result.Success(
                service.getAllTeamByLeagueId(
                    id
                )
            )
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun loadNextMatchesByLeagueId(id: String): Result<MatchDetailsResponse?> {
        return try {
            Result.Success(
                service.getNextMatchByLeagueId(
                    id
                )
            )
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun loadLastMatchesByLeagueId(id: String): Result<MatchDetailsResponse?> {
        return try {
            Result.Success(
                service.getLastMatchByLeagueId(
                    id
                )
            )
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun loadClassementByLeagueId(id: String): Result<ClassementResponse?> {
        return try {
            Result.Success(
                service.getClassementTable(
                    id
                )
            )
        } catch (e: Exception) {
            Result.Error(e)
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