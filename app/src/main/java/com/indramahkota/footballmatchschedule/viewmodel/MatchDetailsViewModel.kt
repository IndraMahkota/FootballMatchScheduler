package com.indramahkota.footballmatchschedule.viewmodel

import androidx.lifecycle.*
import com.indramahkota.footballmatchschedule.data.source.FLeagueRepository
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.TeamDetailsApiResponse
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MatchDetailsViewModel @Inject constructor(private val repository: FLeagueRepository) :
    ViewModel() {

    private val eventId = MutableLiveData<String>()
    var matchDetails: LiveData<Resource<MatchDetailsApiResponse?>> =
        Transformations.switchMap(eventId) { id: String ->
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                emit(repository.loadMatchDetailById( id ))
            }
        }

    private val awayTeamId = MutableLiveData<String>()
    var awayTeamDetails: LiveData<Resource<TeamDetailsApiResponse?>> =
        Transformations.switchMap(awayTeamId) { id: String ->
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                emit(repository.loadTeamDetailById( id ))
            }
        }

    private val homeTeamId = MutableLiveData<String>()
    var homeTeamDetails: LiveData<Resource<TeamDetailsApiResponse?>> =
        Transformations.switchMap(homeTeamId) { id: String ->
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                emit(repository.loadTeamDetailById( id ))
            }
        }

    fun loadMatchDetails(id: String) {
        eventId.value = id
    }

    fun loadHomeTeamDetails(id: String) {
        homeTeamId.value = id
    }

    fun loadAwayTeamDetails(id: String) {
        awayTeamId.value = id
    }
}