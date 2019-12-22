package com.indramahkota.footballapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.indramahkota.footballapp.data.source.FootballAppRepository
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val repository: FootballAppRepository) :
ViewModel() {

    private val favoriteMatchId = MutableLiveData<String>()
    var favoriteMatchById: LiveData<MatchEntity> = Transformations.switchMap(favoriteMatchId) { id: String ->
        repository.loadFavoriteMatchById( id )
    }

    fun getFavoriteMatchById(id: String){
        favoriteMatchId.value = id
    }

    fun getAllFavoriteMatch(): LiveData<List<MatchEntity>>{
        return repository.loadAllFavoriteMatch()
    }

    fun insertFavoriteMatch(match: MatchEntity){
        repository.insertFavoriteMatchById(match)
    }

    fun deleteFavoriteMatch(match: MatchEntity){
        repository.deleteFavoriteMatchById(match)
    }

    fun updateFavoriteMatch(match: MatchEntity){
        repository.updateFavoriteMatchById(match)
    }

    private val favoriteTeamId = MutableLiveData<String>()
    var favoriteTeamById: LiveData<TeamEntity> = Transformations.switchMap(favoriteTeamId) { id: String ->
        repository.loadFavoriteTeamById( id )
    }

    fun getFavoriteTeamById(id: String){
        favoriteTeamId.value = id
    }

    fun getAllFavoriteTeam(): LiveData<List<TeamEntity>>{
        return repository.loadAllFavoriteTeam()
    }

    fun insertFavoriteTeam(team: TeamEntity){
        repository.insertFavoriteTeamById(team)
    }

    fun deleteFavoriteTeam(team: TeamEntity){
        repository.deleteFavoriteTeamById(team)
    }

    fun updateFavoriteTeam(team: TeamEntity){
        repository.updateFavoriteTeamById(team)
    }
}