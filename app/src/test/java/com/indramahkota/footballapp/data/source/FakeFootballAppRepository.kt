package com.indramahkota.footballapp.data.source

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.indramahkota.footballapp.data.source.locale.database.MyDatabase
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.data.source.remote.api.ApiEndPoint
import com.indramahkota.footballapp.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballapp.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballapp.data.source.remote.apiresponse.TeamDetailsApiResponse
import org.jetbrains.anko.db.*

class FakeFootballAppRepository constructor(
    private val api: ApiEndPoint, private val db: MyDatabase ) : FootballAppDataSource {

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

    override fun loadAllFavoriteMatch(): LiveData<List<MatchEntity>> {
        val favoriteMatch: MutableLiveData<List<MatchEntity>> = MutableLiveData()
        try {
            db.use {
                val result = select(MatchEntity.TABLE_NAME)
                val favoriteMatchData = result.parseList(classParser<MatchEntity>())
                favoriteMatch.postValue(favoriteMatchData)
            }
        } catch (e: SQLiteConstraintException){
            e.printStackTrace()
        }
        return favoriteMatch
    }

    override fun loadFavoriteMatchById(id: String): LiveData<MatchEntity> {
        val favoriteMatch: MutableLiveData<MatchEntity> = MutableLiveData()
        try {
            db.use {
                val result = select(MatchEntity.TABLE_NAME)
                    .whereArgs(
                        "${MatchEntity.Column.ID_EVENT} = {id}",
                        "id" to id
                    )
                val favoriteMatchData = result.parseOpt(classParser<MatchEntity>())
                favoriteMatch.postValue(favoriteMatchData)
            }
        } catch (e: SQLiteConstraintException){
            e.printStackTrace()
        }
        return favoriteMatch
    }

    override fun insertFavoriteMatchById(match: MatchEntity) {
        try {
            db.use {
                insert(
                    MatchEntity.TABLE_NAME,
                    MatchEntity.Column.ID_EVENT to match.idEvent,
                    MatchEntity.Column.ID_HOME_TEAM to match.idHomeTeam,
                    MatchEntity.Column.ID_AWAY_TEAM to match.idAwayTeam,
                    MatchEntity.Column.DATE_EVENT to match.dateEvent,
                    MatchEntity.Column.HOME_TEAM to match.strHomeTeam,
                    MatchEntity.Column.AWAY_TEAM to match.strAwayTeam,
                    MatchEntity.Column.HOME_SCORE to match.intHomeScore,
                    MatchEntity.Column.AWAY_SCORE to match.intAwayScore,
                    MatchEntity.Column.HOME_IMAGE to match.sourceHomeImage,
                    MatchEntity.Column.AWAY_IMAGE to match.sourceAwayImage
                )
            }
        } catch (e: SQLiteConstraintException){
            e.printStackTrace()
        }
    }

    override fun deleteFavoriteMatchById(match: MatchEntity) {
        try {
            db.use {
                delete(
                    MatchEntity.TABLE_NAME,
                    "${MatchEntity.Column.ID_EVENT} = {id}",
                    "id" to match.idEvent
                )
            }
        } catch (e: SQLiteConstraintException){
            e.printStackTrace()
        }
    }

    override fun updateFavoriteMatchById(match: MatchEntity) {
        try {
            db.use {
                update(MatchEntity.TABLE_NAME,
                    MatchEntity.Column.ID_HOME_TEAM to match.idHomeTeam,
                            MatchEntity.Column.ID_AWAY_TEAM to match.idAwayTeam,
                            MatchEntity.Column.DATE_EVENT to match.dateEvent,
                            MatchEntity.Column.HOME_TEAM to match.strHomeTeam,
                            MatchEntity.Column.AWAY_TEAM to match.strAwayTeam,
                            MatchEntity.Column.HOME_SCORE to match.intHomeScore,
                            MatchEntity.Column.AWAY_SCORE to match.intAwayScore,
                            MatchEntity.Column.HOME_IMAGE to match.sourceHomeImage,
                            MatchEntity.Column.AWAY_IMAGE to match.sourceAwayImage)
                    .whereSimple("${MatchEntity.Column.ID_EVENT} = ?", match.idEvent)
                    .exec()
            }
        } catch (e: SQLiteConstraintException){
            e.printStackTrace()
        }
    }
}