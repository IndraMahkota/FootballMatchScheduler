package com.indramahkota.footballapp.viewmodel

import androidx.lifecycle.*
import com.indramahkota.footballapp.data.source.repository.FootballAppRepository
import com.indramahkota.footballapp.data.source.repository.Result
import com.indramahkota.footballapp.data.source.remote.model.TeamDetailsResponse
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class TeamDetailsViewModel @Inject constructor(private val repository: FootballAppRepository) :
    ViewModel() {

    private val teamId = MutableLiveData<String>()
    var teamDetails: LiveData<Result<TeamDetailsResponse?>> =
        Transformations.switchMap(teamId) { id: String ->
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                emit(repository.loadTeamDetailById( id ))
            }
        }

    fun loadTeamDetails(id: String) {
        teamId.value = id
    }
}