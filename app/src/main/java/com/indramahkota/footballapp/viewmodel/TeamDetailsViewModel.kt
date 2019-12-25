package com.indramahkota.footballapp.viewmodel

import androidx.lifecycle.*
import com.indramahkota.footballapp.data.source.FootballAppRepository
import com.indramahkota.footballapp.data.source.Resource
import com.indramahkota.footballapp.data.source.remote.apiresponse.TeamDetailsApiResponse
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class TeamDetailsViewModel @Inject constructor(private val repository: FootballAppRepository) :
    ViewModel() {

    private val teamId = MutableLiveData<String>()
    var teamDetails: LiveData<Resource<TeamDetailsApiResponse?>> =
        Transformations.switchMap(teamId) { id: String ->
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                emit(repository.loadTeamDetailById( id ))
            }
        }

    fun loadTeamDetails(id: String) {
        teamId.value = id
    }
}