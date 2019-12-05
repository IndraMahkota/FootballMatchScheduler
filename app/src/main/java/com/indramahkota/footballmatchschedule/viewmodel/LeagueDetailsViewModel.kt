package com.indramahkota.footballmatchschedule.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.indramahkota.footballmatchschedule.data.source.FLeagueRepository
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.LeagueDetailsApiResponse
import javax.inject.Inject

class LeagueDetailsViewModel @Inject constructor(private val repository: FLeagueRepository) :
    ViewModel() {
    private val leagueId = MutableLiveData<String>()
    var leagueDetails: LiveData<Resource<LeagueDetailsApiResponse?>> =
        Transformations.switchMap(leagueId) { id: String ->
            repository.loadLeagueDetailsByLeagueId( id )
        }

    fun loadLeagueDetails(id: String) {
        leagueId.value = id
    }
}