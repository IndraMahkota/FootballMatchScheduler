package com.indramahkota.footballmatchschedule.viewmodel

import androidx.lifecycle.*
import com.indramahkota.footballmatchschedule.data.source.FLeagueRepository
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.locale.entity.MatchEntity
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.MatchDetailsApiModel
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.TeamDetailsApiModel
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.TeamDetailsApiResponse
import kotlinx.coroutines.*
import javax.inject.Inject

class LeagueDetailsViewModel @Inject constructor(private val repository: FLeagueRepository) :
    ViewModel() {

    private var nextHelper = arrayListOf<MatchEntity>()
    private var prevHelper = arrayListOf<MatchEntity>()
    var newNextMatchData: MutableLiveData<Resource<List<MatchEntity>?>> = MutableLiveData()
    var newPrevMatchData: MutableLiveData<Resource<List<MatchEntity>?>> = MutableLiveData()

    private val leagueId = MutableLiveData<String>()
    var leagueDetails: LiveData<Resource<LeagueDetailsApiResponse?>> =
        Transformations.switchMap(leagueId) { id: String ->
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                emit(repository.loadLeagueDetailsByLeagueId( id ))
            }
        }

    fun loadAllDetails(id: String) {
        leagueId.value = id

        viewModelScope.launch {
            val allTeamResource = async { repository.loadAllTeamByLeagueId(id) }
            val nextMatchResource = async { repository.loadNextMatchesByLeagueId(id) }
            val prevMatchResource = async { repository.loadLastMatchesByLeagueId(id) }

            setNewNextMatchData(allTeamResource.await(), nextMatchResource.await())
            setNewPrevMatchData(allTeamResource.await(), prevMatchResource.await())
        }
    }

    private fun setNewNextMatchData(all: Resource<TeamDetailsApiResponse?>,
                                      next: Resource<MatchDetailsApiResponse?>) {
        var newNextMatches = arrayListOf<MatchEntity>()
        if(all.isSuccess && next.isSuccess) {
            newNextMatches = createNewListData(all.data?.teams, next.data?.events)
            newNextMatchData.postValue(Resource.success(newNextMatches))
        } else {
            newNextMatchData.postValue(Resource.error(next.message, null))
        }
        nextHelper = ArrayList(newNextMatches)
    }

    private fun setNewPrevMatchData(all: Resource<TeamDetailsApiResponse?>,
                                      prev: Resource<MatchDetailsApiResponse?>) {
        var newPrevMatches = arrayListOf<MatchEntity>()
        if(all.isSuccess && prev.isSuccess) {
            newPrevMatches = createNewListData(all.data?.teams, prev.data?.events)
            newPrevMatchData.postValue(Resource.success(newPrevMatches))
        } else {
            newPrevMatchData.postValue(Resource.error(prev.message, null))
        }
        prevHelper = ArrayList(newPrevMatches)
    }

    private fun createNewListData(all: List<TeamDetailsApiModel>?,
                                  dataList: List<MatchDetailsApiModel>?):ArrayList<MatchEntity>{
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
                    if(listHelper.size == 2) {
                        if(listHelper[0].idTeam.equals(dataList[i].idHomeTeam)){
                            srcImgHomeTeam = listHelper[0].strTeamBadge ?: ""
                            srcImgAwayTeam = listHelper[1].strTeamBadge ?: ""
                        } else {
                            srcImgAwayTeam = listHelper[0].strTeamBadge ?: ""
                            srcImgHomeTeam = listHelper[1].strTeamBadge ?: ""
                        }
                    } else if (listHelper.size == 1) {
                        if(listHelper[0].idTeam.equals(dataList[i].idHomeTeam)){
                            srcImgHomeTeam = listHelper[0].strTeamBadge ?: ""
                        } else {
                            srcImgAwayTeam = listHelper[0].strTeamBadge ?: ""
                        }
                    }
                }

                newMatchList.add(MatchEntity(dataList[i].idEvent ?: "",
                    dataList[i].idHomeTeam ?: "", dataList[i].idAwayTeam?: "",
                    dataList[i].dateEvent ?: "-",dataList[i].strHomeTeam ?: "-",
                    dataList[i].strAwayTeam ?: "-",dataList[i].intHomeScore ?: "-",
                    dataList[i].intAwayScore ?: "-", srcImgHomeTeam, srcImgAwayTeam))
            }
        }

        return newMatchList
    }

    fun getAllMatchsData(): List<MatchEntity>{
        val arrayList = arrayListOf<MatchEntity>()
        arrayList.addAll(nextHelper)
        arrayList.addAll(prevHelper)
        return arrayList
    }
}