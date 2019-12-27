package com.indramahkota.footballapp.viewmodel

import androidx.lifecycle.*
import com.indramahkota.footballapp.data.source.FootballAppRepository
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val repository: FootballAppRepository) :
ViewModel() {

    private val favoriteMatchId = MutableLiveData<String>()
    var favoriteMatchById: LiveData<MatchEntity> = Transformations.switchMap(favoriteMatchId) { id: String ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(repository.loadFavoriteMatchById( id ))
        }
    }

    fun getFavoriteMatchById(id: String){
        favoriteMatchId.value = id
    }

    fun getAllFavoriteMatch(): LiveData<List<MatchEntity>>{
        return liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(repository.loadAllFavoriteMatch())
        }
    }

    fun insertFavoriteMatch(match: MatchEntity){
        viewModelScope.launch {
            repository.insertFavoriteMatchById(match)
        }
    }

    fun deleteFavoriteMatch(match: MatchEntity){
        viewModelScope.launch {
            repository.deleteFavoriteMatchById(match)
        }
    }

    fun updateFavoriteMatch(match: MatchEntity){
        viewModelScope.launch {
            repository.updateFavoriteMatchById(match)
        }
    }

    private val favoriteTeamId = MutableLiveData<String>()
    var favoriteTeamById: LiveData<TeamEntity> = Transformations.switchMap(favoriteTeamId) { id: String ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(repository.loadFavoriteTeamById( id ))
        }
    }

    fun getFavoriteTeamById(id: String){
        favoriteTeamId.value = id
    }

    fun getAllFavoriteTeam(): LiveData<List<TeamEntity>>{
        return liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(repository.loadAllFavoriteTeam())
        }
    }

    fun insertFavoriteTeam(team: TeamEntity){
        viewModelScope.launch {
            repository.insertFavoriteTeamById(team)
        }
    }

    fun deleteFavoriteTeam(team: TeamEntity){
        viewModelScope.launch {
            repository.deleteFavoriteTeamById(team)
        }
    }

    fun updateFavoriteTeam(team: TeamEntity){
        viewModelScope.launch {
            repository.updateFavoriteTeamById(team)
        }
    }
}