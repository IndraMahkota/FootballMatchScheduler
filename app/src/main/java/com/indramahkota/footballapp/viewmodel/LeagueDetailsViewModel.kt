package com.indramahkota.footballapp.viewmodel

import androidx.lifecycle.*
import com.indramahkota.footballapp.data.source.repository.FootballAppRepository
import com.indramahkota.footballapp.data.source.repository.Result
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity
import com.indramahkota.footballapp.data.source.remote.model.ClassementModel
import com.indramahkota.footballapp.data.source.remote.model.MatchDetailsModel
import com.indramahkota.footballapp.data.source.remote.model.TeamDetailsiModel
import com.indramahkota.footballapp.data.source.remote.model.ClassementResponse
import com.indramahkota.footballapp.data.source.remote.model.LeagueDetailsResponse
import com.indramahkota.footballapp.data.source.remote.model.MatchDetailsResponse
import com.indramahkota.footballapp.data.source.remote.model.TeamDetailsResponse
import com.indramahkota.footballapp.utilities.toListTeamEntity
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject

class LeagueDetailsViewModel @Inject constructor(private val repository: FootballAppRepository) :
    ViewModel() {

    private var teamHelper = arrayListOf<TeamEntity>()
    private var nextHelper = arrayListOf<MatchEntity>()
    private var prevHelper = arrayListOf<MatchEntity>()

    var allTeamData: MutableLiveData<Result<List<TeamEntity>?>> = MutableLiveData()
    var newNextMatchData: MutableLiveData<Result<List<MatchEntity>?>> = MutableLiveData()
    var newPrevMatchData: MutableLiveData<Result<List<MatchEntity>?>> = MutableLiveData()
    var allClassementData: MutableLiveData<Result<List<ClassementModel>?>> = MutableLiveData()

    private val leagueId = MutableLiveData<String>()
    var leagueDetails: LiveData<Result<LeagueDetailsResponse?>> =
        Transformations.switchMap(leagueId) { id: String ->
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                emit(repository.loadLeagueDetailsByLeagueId(id))
            }
        }

    fun loadAllDetails(id: String) {
        leagueId.value = id

        viewModelScope.launch {
            val allTeamResource = async { repository.loadAllTeamByLeagueId(id) }
            val nextMatchResource = async { repository.loadNextMatchesByLeagueId(id) }
            val prevMatchResource = async { repository.loadLastMatchesByLeagueId(id) }
            val allClassementResource = async { repository.loadClassementByLeagueId(id) }

            val allTeam = allTeamResource.await()

            setAllTeamData(allTeam)
            setAllClassementData(allTeam, allClassementResource.await())
            setNewNextMatchData(allTeam, nextMatchResource.await())
            setNewPrevMatchData(allTeam, prevMatchResource.await())
        }
    }

    private fun setAllTeamData(it: Result<TeamDetailsResponse?>) {
        when (it) {
            is Result.Success -> {
                val listAllTeam = it.data?.teams.toListTeamEntity()
                allTeamData.postValue(Result.Success(listAllTeam))
                teamHelper = ArrayList(listAllTeam)
            }
        }
    }

    private fun setNewNextMatchData(
        all: Result<TeamDetailsResponse?>,
        next: Result<MatchDetailsResponse?>
    ) {
        var newNextMatches = arrayListOf<MatchEntity>()
        if (all is Result.Success && next is Result.Success) {
            newNextMatches = createNewListData(all.data?.teams, next.data?.events)
            newNextMatchData.postValue(Result.Success(newNextMatches))
        } else {
            newNextMatchData.postValue(Result.Error(Exception("Error Parsing")))
        }
        nextHelper = ArrayList(newNextMatches)
    }

    private fun setNewPrevMatchData(
        all: Result<TeamDetailsResponse?>,
        prev: Result<MatchDetailsResponse?>
    ) {
        var newPrevMatches = arrayListOf<MatchEntity>()
        if (all is Result.Success && prev is Result.Success) {
            newPrevMatches = createNewListData(all.data?.teams, prev.data?.events)
            newPrevMatchData.postValue(Result.Success(newPrevMatches))
        } else {
            newPrevMatchData.postValue(Result.Error(Exception("Error Parsing")))
        }
        prevHelper = ArrayList(newPrevMatches)
    }

    private fun createNewListData(
        all: List<TeamDetailsiModel>?,
        dataList: List<MatchDetailsModel>?
    ): ArrayList<MatchEntity> {
        val newMatchList = arrayListOf<MatchEntity>()

        if (dataList != null) {
            for (i in dataList.indices) {
                val listHelper = all?.filter {
                    it.idTeam.equals(dataList[i].idHomeTeam) ||
                            it.idTeam.equals(dataList[i].idAwayTeam)
                }
                var srcImgHomeTeam = ""
                var srcImgAwayTeam = ""

                if (listHelper != null) {
                    if (listHelper.size == 2) {
                        if (listHelper[0].idTeam.equals(dataList[i].idHomeTeam)) {
                            srcImgHomeTeam = listHelper[0].strTeamBadge ?: ""
                            srcImgAwayTeam = listHelper[1].strTeamBadge ?: ""
                        } else {
                            srcImgAwayTeam = listHelper[0].strTeamBadge ?: ""
                            srcImgHomeTeam = listHelper[1].strTeamBadge ?: ""
                        }
                    } else if (listHelper.size == 1) {
                        if (listHelper[0].idTeam.equals(dataList[i].idHomeTeam)) {
                            srcImgHomeTeam = listHelper[0].strTeamBadge ?: ""
                        } else {
                            srcImgAwayTeam = listHelper[0].strTeamBadge ?: ""
                        }
                    }
                }

                newMatchList.add(
                    MatchEntity(
                        dataList[i].idEvent ?: "",
                        dataList[i].idHomeTeam ?: "", dataList[i].idAwayTeam ?: "",
                        dataList[i].dateEvent ?: "-", dataList[i].strHomeTeam ?: "-",
                        dataList[i].strAwayTeam ?: "-", dataList[i].intHomeScore ?: "-",
                        dataList[i].intAwayScore ?: "-", srcImgHomeTeam, srcImgAwayTeam
                    )
                )
            }
        }

        return newMatchList
    }

    private fun setAllClassementData(
        all: Result<TeamDetailsResponse?>,
        classement: Result<ClassementResponse?>
    ) {
        val newClassementList = arrayListOf<ClassementModel>()

        var allTeam: List<TeamDetailsiModel>? = null
        if (all is Result.Success) {
            allTeam = all.data?.teams
        }

        var classementList: List<ClassementModel>? = null
        if (classement is Result.Success) {
            classementList = classement.data?.table
        }

        if (allTeam != null && classementList != null) {
            for (classementApiModel in classementList) {
                for (teamDetailsApiModel in allTeam) {
                    if (teamDetailsApiModel.strTeam.equals(classementApiModel.name)) {
                        classementApiModel.image = teamDetailsApiModel.strTeamBadge
                        newClassementList.add(classementApiModel)
                        break
                    }
                }
            }
            allClassementData.postValue(Result.Success(newClassementList))

        } else {
            allClassementData.postValue(Result.Error(Exception("Error Parsing")))
        }
    }

    fun getAllMatchsData(): ArrayList<MatchEntity> {
        val arrayList = arrayListOf<MatchEntity>()
        arrayList.addAll(nextHelper)
        arrayList.addAll(prevHelper)
        return arrayList
    }

    fun getAllTeamData(): ArrayList<TeamEntity> {
        return teamHelper
    }
}