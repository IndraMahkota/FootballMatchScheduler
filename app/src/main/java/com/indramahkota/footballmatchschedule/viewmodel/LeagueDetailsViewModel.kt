package com.indramahkota.footballmatchschedule.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.indramahkota.footballmatchschedule.data.source.FLeagueRepository
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.MatchDetailsApiModel
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.TeamDetailsApiModel
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.TeamDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.locale.entity.MatchEntity
import javax.inject.Inject

class LeagueDetailsViewModel @Inject constructor(private val repository: FLeagueRepository) :
    ViewModel() {

    private var nextHelper = arrayListOf<MatchEntity>()
    private var prevHelper = arrayListOf<MatchEntity>()
    var newNextMatchesData: MutableLiveData<List<MatchEntity>> = MutableLiveData()
    var newPrevMatchesData: MutableLiveData<List<MatchEntity>> = MutableLiveData()

    private val leagueId = MutableLiveData<String>()

    var leagueDetails: LiveData<Resource<LeagueDetailsApiResponse?>> =
        Transformations.switchMap(leagueId) { id: String ->
            repository.loadLeagueDetailsByLeagueId( id )
        }

    var allTeamInLeague: LiveData<Resource<TeamDetailsApiResponse?>> =
        Transformations.switchMap(leagueId) { id: String ->
            repository.loadAllTeamByLeagueId( id )
        }

    var nextMatches: LiveData<Resource<MatchDetailsApiResponse?>> =
        Transformations.switchMap(leagueId) { id: String ->
            repository.loadNextMatchesByLeagueId( id )
        }

    var prevMatches: LiveData<Resource<MatchDetailsApiResponse?>> =
        Transformations.switchMap(leagueId) { id: String ->
            repository.loadLastMatchesByLeagueId( id )
        }

    fun loadAllDetails(id: String) {
        leagueId.value = id
    }

    fun setNewNextMatchesData(all: List<TeamDetailsApiModel>,
                              next: List<MatchDetailsApiModel>) {
        val newNextMatches: MutableList<MatchEntity> = mutableListOf()

        for (i in next.indices) {
            val listHelper = all.filter {
                it.idTeam.equals(next[i].idHomeTeam) ||
                        it.idTeam.equals(next[i].idAwayTeam)
            }

            var srcImgHomeTeam = ""
            var srcImgAwayTeam = ""

            if(listHelper.size == 2) {
                if(listHelper[0].idTeam.equals(next[i].idHomeTeam)){
                    srcImgHomeTeam = listHelper[0].strTeamBadge ?: ""
                    srcImgAwayTeam = listHelper[1].strTeamBadge ?: ""
                } else {
                    srcImgAwayTeam = listHelper[0].strTeamBadge ?: ""
                    srcImgHomeTeam = listHelper[1].strTeamBadge ?: ""
                }
            } else if (listHelper.size == 1) {
                if(listHelper[0].idTeam.equals(next[i].idHomeTeam)){
                    srcImgHomeTeam = listHelper[0].strTeamBadge ?: ""
                } else {
                    srcImgAwayTeam = listHelper[0].strTeamBadge ?: ""
                }
            }

            newNextMatches.add(MatchEntity(next[i].idEvent ?: "",
                next[i].idHomeTeam ?: "", next[i].idAwayTeam?: "",
                next[i].dateEvent ?: "-", next[i].strHomeTeam ?: "-",
                next[i].strAwayTeam ?: "-", next[i].intHomeScore ?: "-",
                next[i].intAwayScore ?: "-", srcImgHomeTeam, srcImgAwayTeam))
        }

        nextHelper = ArrayList(newNextMatches)
        newNextMatchesData.postValue(newNextMatches)
    }

    fun setNewPrevMatchesData(all: List<TeamDetailsApiModel>,
                              prev: List<MatchDetailsApiModel>) {
        val newPrevMatches: MutableList<MatchEntity> = mutableListOf()

        for (i in prev.indices) {
            val listHelper = all.filter {
                it.idTeam.equals(prev[i].idHomeTeam) ||
                        it.idTeam.equals(prev[i].idAwayTeam)
            }

            var srcImgHomeTeam = ""
            var srcImgAwayTeam = ""

            if(listHelper.size == 2) {
                if(listHelper[0].idTeam.equals(prev[i].idHomeTeam)){
                    srcImgHomeTeam = listHelper[0].strTeamBadge ?: ""
                    srcImgAwayTeam = listHelper[1].strTeamBadge ?: ""
                } else {
                    srcImgAwayTeam = listHelper[0].strTeamBadge ?: ""
                    srcImgHomeTeam = listHelper[1].strTeamBadge ?: ""
                }
            } else if (listHelper.size == 1) {
                if(listHelper[0].idTeam.equals(prev[i].idHomeTeam)){
                    srcImgHomeTeam = listHelper[0].strTeamBadge ?: ""
                } else {
                    srcImgAwayTeam = listHelper[0].strTeamBadge ?: ""
                }
            }

            newPrevMatches.add(MatchEntity(prev[i].idEvent ?: "",
                prev[i].idHomeTeam ?: "", prev[i].idAwayTeam?: "",
                prev[i].dateEvent ?: "-",prev[i].strHomeTeam ?: "-",
                prev[i].strAwayTeam ?: "-",prev[i].intHomeScore ?: "-",
                prev[i].intAwayScore ?: "-", srcImgHomeTeam, srcImgAwayTeam))
        }

        prevHelper = ArrayList(newPrevMatches)
        newPrevMatchesData.postValue(newPrevMatches)
    }

    fun getAllMatchsData(): List<MatchEntity>{
        val arrayList = arrayListOf<MatchEntity>()
        arrayList.addAll(nextHelper)
        arrayList.addAll(prevHelper)
        return arrayList
    }
}