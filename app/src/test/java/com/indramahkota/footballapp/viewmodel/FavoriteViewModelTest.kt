package com.indramahkota.footballapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.indramahkota.footballapp.UnitTestFakeData
import com.indramahkota.footballapp.UnitTestFakeData.generateListMatchEntity
import com.indramahkota.footballapp.data.source.FootballAppRepository
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FavoriteViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository: FootballAppRepository = mock()
    private val observerDataById: Observer<MatchEntity> = mock()
    private val observerAllData: Observer<List<MatchEntity>> = mock()

    private lateinit var viewModel: FavoriteViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = FavoriteViewModel(repository)
    }

    @Test
    fun `Check value when get favorite by Id`() {
        val id = "1234"
        val data = UnitTestFakeData.generateMatchEntity(id)
        val liveData: MutableLiveData<MatchEntity> = MutableLiveData()
        liveData.value = data

        Mockito.`when`(repository.loadFavoriteMatchById( id ))
            .thenReturn(liveData)

        viewModel.favoriteMatchById.observeForever(observerDataById)

        viewModel.getFavoriteMatchById(id)

        Mockito.verify(observerDataById).onChanged(data)
        Assert.assertEquals(viewModel.favoriteMatchById.value, data)
    }

    @Test
    fun `Check value when get list all favorite`() {
        val id = "1234"
        val data = generateListMatchEntity(id)
        val liveData: MutableLiveData<List<MatchEntity>> = MutableLiveData()
        liveData.value = data

        Mockito.`when`(repository.loadAllFavoriteMatch())
            .thenReturn(liveData)

        viewModel.getAllFavoriteMatch().observeForever(observerAllData)
        
        viewModel.getAllFavoriteMatch()

        Mockito.verify(observerAllData).onChanged(data)
        Assert.assertEquals(viewModel.getAllFavoriteMatch().value, data)
    }
}