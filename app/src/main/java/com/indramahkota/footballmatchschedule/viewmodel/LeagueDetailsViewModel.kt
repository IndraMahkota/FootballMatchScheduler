package com.indramahkota.footballmatchschedule.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.indramahkota.footballmatchschedule.data.source.FLeagueRepository
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.TeamDetailsApiResponse
import javax.inject.Inject

class LeagueDetailsViewModel @Inject constructor(private val repository: FLeagueRepository) :
    ViewModel() {

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
}