package com.indramahkota.footballmatchschedule.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.indramahkota.footballmatchschedule.FakeData.generateListMatchDetailsApiModel
import com.indramahkota.footballmatchschedule.FakeData.generateListTeamDetailsApiModel
import com.indramahkota.footballmatchschedule.MainCoroutineRule
import com.indramahkota.footballmatchschedule.data.source.FLeagueRepository
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.TeamDetailsApiResponse
import com.indramahkota.footballmatchschedule.getOrAwaitValue
import com.indramahkota.footballmatchschedule.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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

    private val repository: FLeagueRepository = mock()

    private lateinit var viewModel: MatchDetailsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MatchDetailsViewModel(repository)
    }

    @Test
    fun getMatchDetailsTest() = runBlocking {
        val id = "1234"
        val data = MatchDetailsApiResponse(generateListMatchDetailsApiModel())
        val resourceData: Resource<MatchDetailsApiResponse?> = Resource.success(data)

        Mockito.`when`(repository.loadMatchDetailById( id ))
            .thenReturn(resourceData)

        viewModel.loadMatchDetails(id)

        val transformed = viewModel.matchDetails.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        assertEquals(resourceData.data?.events, transformed.data?.events)
    }

    @Test
    fun getAwayTeamDetails() = runBlocking {
        val id = "1234"
        val data = TeamDetailsApiResponse(generateListTeamDetailsApiModel())
        val resourceData: Resource<TeamDetailsApiResponse?> = Resource.success(data)

        Mockito.`when`(repository.loadTeamDetailById( id ))
            .thenReturn(resourceData)

        viewModel.loadAwayTeamDetails(id)

        val transformed = viewModel.awayTeamDetails.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        assertEquals(resourceData.data?.teams, transformed.data?.teams)
    }

    @Test
    fun getHomeTeamDetails() = runBlocking {
        val id = "1234"
        val data = TeamDetailsApiResponse(generateListTeamDetailsApiModel())
        val resourceData: Resource<TeamDetailsApiResponse?> = Resource.success(data)

        Mockito.`when`(repository.loadTeamDetailById( id ))
            .thenReturn(resourceData)

        viewModel.loadHomeTeamDetails(id)

        val transformed = viewModel.homeTeamDetails.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        assertEquals(resourceData.data?.teams, transformed.data?.teams)
    }
}