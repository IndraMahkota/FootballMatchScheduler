package com.indramahkota.footballmatchschedule.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.indramahkota.footballmatchschedule.data.source.FLeagueRepository
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import javax.inject.Inject

class MatchViewModel @Inject constructor(private val repository: FLeagueRepository) :
    ViewModel() {

    private val leagueId = MutableLiveData<String>()
    var nextMatches: LiveData<Resource<MatchDetailsApiResponse?>> =
        Transformations.switchMap(leagueId) { id: String ->
            repository.loadNextMatchesByLeagueId( id )
        }

    var prevMatches: LiveData<Resource<MatchDetailsApiResponse?>> =
        Transformations.switchMap(leagueId) { id: String ->
            repository.loadLastMatchesByLeagueId( id )
        }

    fun loadNextMatches(id: String) {
        leagueId.value = id
    }

    fun loadPrevMatches(id: String) {
        leagueId.value = id
    }
}