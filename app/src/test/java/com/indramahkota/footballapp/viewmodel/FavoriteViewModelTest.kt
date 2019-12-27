package com.indramahkota.footballapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.indramahkota.footballapp.MainCoroutineRule
import com.indramahkota.footballapp.UnitTestFakeData.generateListMatchEntity
import com.indramahkota.footballapp.UnitTestFakeData.generateListTeamEntity
import com.indramahkota.footballapp.UnitTestFakeData.generateMatchEntity
import com.indramahkota.footballapp.UnitTestFakeData.generateTeamDetailsApiModel
import com.indramahkota.footballapp.data.source.FootballAppRepository
import com.indramahkota.footballapp.getOrAwaitValue
import com.indramahkota.footballapp.mock
import com.indramahkota.footballapp.utilities.toTeamEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class FavoriteViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val repository: FootballAppRepository = mock()

    private lateinit var viewModel: FavoriteViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = FavoriteViewModel(repository)
    }

    @Test
    fun `Check value when get favorite match by Id`() = mainCoroutineRule.runBlockingTest {
        val id = "1234"
        val data = generateMatchEntity(id)

        viewModel.getFavoriteMatchById(id)

        val transformed = viewModel.favoriteMatchById.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        Mockito.`when`(repository.loadFavoriteMatchById( id ))
            .thenReturn(data)

        Assert.assertEquals(viewModel.favoriteMatchById.value, transformed)
    }

    @Test
    fun `Check value when get list all favorite match`() = mainCoroutineRule.runBlockingTest {
        val id = "1234"
        val data = generateListMatchEntity(id)

        val transformed = viewModel.getAllFavoriteMatch().getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        Mockito.`when`(repository.loadAllFavoriteMatch())
            .thenReturn(data)

        Assert.assertEquals(viewModel.getAllFavoriteMatch().value, transformed)
    }

    @Test
    fun `Check value when get favorite team by Id`() = mainCoroutineRule.runBlockingTest {
        val id = "1234"
        val data = generateTeamDetailsApiModel(id).toTeamEntity()

        viewModel.getFavoriteTeamById(id)

        val transformed = viewModel.favoriteTeamById.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        Mockito.`when`(repository.loadFavoriteTeamById( id ))
            .thenReturn(data)

        Assert.assertEquals(viewModel.favoriteTeamById.value, transformed)
    }

    @Test
    fun `Check value when get list all favorite team`() = mainCoroutineRule.runBlockingTest {
        val data = generateListTeamEntity()

        val transformed = viewModel.getAllFavoriteTeam().getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        Mockito.`when`(repository.loadAllFavoriteTeam())
            .thenReturn(data)

        Assert.assertEquals(viewModel.getAllFavoriteTeam().value, transformed)
    }
}