package com.indramahkota.footballmatchschedule.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.indramahkota.footballmatchschedule.FakeData
import com.indramahkota.footballmatchschedule.FakeData.generateListMatchEntity
import com.indramahkota.footballmatchschedule.data.source.FLeagueRepository
import com.indramahkota.footballmatchschedule.data.source.locale.entity.MatchEntity
import com.indramahkota.footballmatchschedule.mockito.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FavoriteMatchViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository: FLeagueRepository = mock()
    private val observerDataById: Observer<MatchEntity> = mock()
    private val observerAllData: Observer<List<MatchEntity>> = mock()

    private lateinit var viewModel: FavoriteMatchViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = FavoriteMatchViewModel(repository)
    }

    @Test
    fun getFavoriteById() {
        val id = "1234"
        val data = FakeData.generateMatchEntity(id)
        val liveData: MutableLiveData<MatchEntity> = MutableLiveData()
        liveData.value = data

        Mockito.`when`(repository.loadFavoriteMatchById( id ))
            .thenReturn(liveData)

        viewModel.favoriteById.observeForever(observerDataById)

        viewModel.getFavoriteById(id)

        Mockito.verify(observerDataById).onChanged(data)
    }

    @Test
    fun getAllFavorite() {
        val id = "1234"
        val data = generateListMatchEntity(id)
        val liveData: MutableLiveData<List<MatchEntity>> = MutableLiveData()
        liveData.value = data

        Mockito.`when`(repository.loadAllFavoriteMatch())
            .thenReturn(liveData)

        viewModel.getAllFavorite().observeForever(observerAllData)
        
        viewModel.getAllFavorite()

        Mockito.verify(observerAllData).onChanged(data)
    }
}