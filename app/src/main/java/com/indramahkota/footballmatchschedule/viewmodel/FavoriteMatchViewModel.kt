package com.indramahkota.footballmatchschedule.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.indramahkota.footballmatchschedule.data.source.FLeagueRepository
import com.indramahkota.footballmatchschedule.data.source.locale.entity.MatchEntity
import javax.inject.Inject

class FavoriteMatchViewModel @Inject constructor(private val repository: FLeagueRepository) :
ViewModel() {

    private val favoriteId = MutableLiveData<String>()
    var favoriteById: LiveData<MatchEntity> = Transformations.switchMap(favoriteId) { id: String ->
        repository.loadFavoriteMatchById( id )
    }

    fun getFavoriteById(id: String){
        favoriteId.value = id
    }

    fun getAllFavorite(): LiveData<List<MatchEntity>>{
        return repository.loadAllFavoriteMatch()
    }

    fun insertFavorite(match: MatchEntity){
        repository.insertFavoriteMatchById(match)
    }

    fun deleteFavorite(match: MatchEntity){
        repository.deleteFavoriteMatchById(match)
    }

    fun updateFavorite(match: MatchEntity){
        repository.updateFavoriteMatchById(match)
    }
}