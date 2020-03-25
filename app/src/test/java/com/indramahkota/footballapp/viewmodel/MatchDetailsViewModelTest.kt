package com.indramahkota.footballapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.indramahkota.footballapp.UnitTestFakeData.generateListMatchDetailsApiModel
import com.indramahkota.footballapp.UnitTestFakeData.generateListTeamDetailsApiModel
import com.indramahkota.footballapp.MainCoroutineRule
import com.indramahkota.footballapp.data.source.FootballAppRepository
import com.indramahkota.footballapp.data.source.Resource
import com.indramahkota.footballapp.data.source.remote.model.MatchDetailsResponse
import com.indramahkota.footballapp.data.source.remote.model.TeamDetailsResponse
import com.indramahkota.footballapp.getOrAwaitValue
import com.indramahkota.footballapp.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MatchDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val repository: FootballAppRepository = mock()

    private lateinit var viewModel: MatchDetailsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MatchDetailsViewModel(repository)
    }

    @Test
    fun `Check success value when get Match Details by Event Id`() = mainCoroutineRule.runBlockingTest {
        val id = "1234"
        val data =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val resourceData: Resource<MatchDetailsResponse?> = Resource.success(data)

        Mockito.`when`(repository.loadMatchDetailById( id ))
            .thenReturn(resourceData)

        viewModel.loadMatchDetails(id)

        val transformed = viewModel.matchDetails.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        assertEquals(resourceData.data?.events, transformed.data?.events)
    }

    @Test
    fun `Check success value when get Away Team by Away Team Id`() = mainCoroutineRule.runBlockingTest {
        val id = "1234"
        val data =
            TeamDetailsResponse(
                generateListTeamDetailsApiModel()
            )
        val resourceData: Resource<TeamDetailsResponse?> = Resource.success(data)

        Mockito.`when`(repository.loadTeamDetailById( id ))
            .thenReturn(resourceData)

        viewModel.loadAwayTeamDetails(id)

        val transformed = viewModel.awayTeamDetails.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        assertEquals(resourceData.data?.teams, transformed.data?.teams)
    }

    @Test
    fun `Check success value when get Home Team by Home Team Id`() = mainCoroutineRule.runBlockingTest {
        val id = "1234"
        val data =
            TeamDetailsResponse(
                generateListTeamDetailsApiModel()
            )
        val resourceData: Resource<TeamDetailsResponse?> = Resource.success(data)

        Mockito.`when`(repository.loadTeamDetailById( id ))
            .thenReturn(resourceData)

        viewModel.loadHomeTeamDetails(id)

        val transformed = viewModel.homeTeamDetails.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        assertEquals(resourceData.data?.teams, transformed.data?.teams)
    }
}