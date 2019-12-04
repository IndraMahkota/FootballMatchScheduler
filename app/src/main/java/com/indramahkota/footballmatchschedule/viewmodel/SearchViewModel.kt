package com.indramahkota.footballmatchschedule.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.indramahkota.footballmatchschedule.data.source.FLeagueRepository
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: FLeagueRepository) :
    ViewModel() {

    private val querySearch = MutableLiveData<String>()
    var searchEvent: LiveData<Resource<MatchDetailsApiResponse?>> =
        Transformations.switchMap(querySearch) { query: String ->
            repository.searchMatchEvent( query )
        }

    fun searchEventByQuery(query: String) {
        querySearch.value = query
    }
}