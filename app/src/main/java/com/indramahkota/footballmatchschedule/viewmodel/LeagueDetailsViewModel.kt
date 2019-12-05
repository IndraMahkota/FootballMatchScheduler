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
import com.indramahkota.footballmatchschedule.data.source.remote.model.MatchModel
import javax.inject.Inject

class LeagueDetailsViewModel @Inject constructor(private val repository: FLeagueRepository) :
    ViewModel() {

    var newNextMatchesData: MutableLiveData<List<MatchModel>> = MutableLiveData()
    var newPrevMatchesData: MutableLiveData<List<MatchModel>> = MutableLiveData()

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
        val newNextMatches: MutableList<MatchModel> = mutableListOf()

        for (i in next.indices) {
            val srcImgHomeTeam: String
            val srcImgAwayTeam: String

            val listHelper = all.filter {
                it.idTeam.equals(next[i].idHomeTeam) ||
                        it.idTeam.equals(next[i].idAwayTeam)
            }

            if(listHelper[0].idTeam.equals(next[i].idHomeTeam)){
                srcImgHomeTeam = listHelper[0].strTeamBadge.toString()
                srcImgAwayTeam = listHelper[1].strTeamBadge.toString()
            } else {
                srcImgHomeTeam = listHelper[1].strTeamBadge.toString()
                srcImgAwayTeam = listHelper[0].strTeamBadge.toString()
            }

            newNextMatches.add(MatchModel(next[i].idEvent ?: "",
                next[i].idHomeTeam ?: "", next[i].idAwayTeam?: "",
                next[i].dateEvent ?: "-", next[i].strHomeTeam ?: "-",
                next[i].strAwayTeam ?: "-", next[i].intHomeScore ?: "-",
                next[i].intAwayScore ?: "-", srcImgHomeTeam, srcImgAwayTeam))
        }

        newNextMatchesData.postValue(newNextMatches)
    }

    fun setNewPrevMatchesData(all: List<TeamDetailsApiModel>,
                              prev: List<MatchDetailsApiModel>) {
        val newPrevMatches: MutableList<MatchModel> = mutableListOf()

        for (i in prev.indices) {
            val srcImgHomeTeam: String
            val srcImgAwayTeam: String

            val listHelper = all.filter {
                it.idTeam.equals(prev[i].idHomeTeam) ||
                        it.idTeam.equals(prev[i].idAwayTeam)
            }

            if(listHelper[0].idTeam.equals(prev[i].idHomeTeam)){
                srcImgHomeTeam = listHelper[0].strTeamBadge.toString()
                srcImgAwayTeam = listHelper[1].strTeamBadge.toString()
            } else {
                srcImgHomeTeam = listHelper[1].strTeamBadge.toString()
                srcImgAwayTeam = listHelper[0].strTeamBadge.toString()
            }

            newPrevMatches.add(MatchModel(prev[i].idEvent ?: "",
                prev[i].idHomeTeam ?: "", prev[i].idAwayTeam?: "",
                prev[i].dateEvent ?: "-",prev[i].strHomeTeam ?: "-",
                prev[i].strAwayTeam ?: "-",prev[i].intHomeScore ?: "-",
                prev[i].intAwayScore ?: "-", srcImgHomeTeam, srcImgAwayTeam))
        }

        newPrevMatchesData.postValue(newPrevMatches)
    }
}