package com.indramahkota.footballmatchschedule.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.indramahkota.footballmatchschedule.FakeData.generateListMatchDetailsApiModel
import com.indramahkota.footballmatchschedule.FakeData.generateListTeamDetailsApiModel
import com.indramahkota.footballmatchschedule.MainCoroutineRule
import com.indramahkota.footballmatchschedule.data.source.FLeagueRepository
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.TeamDetailsApiResponse
import com.indramahkota.footballmatchschedule.getOrAwaitValue
import com.indramahkota.footballmatchschedule.mockito.mock
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

        val liveData: MutableLiveData<Resource<MatchDetailsApiResponse?>> = MutableLiveData()
        liveData.value = resourceData

        Mockito.`when`(repository.loadMatchDetailById( id ))
            .thenReturn(resourceData)

        viewModel.loadMatchDetails(id)

        val transformed = viewModel.matchDetails.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        assertEquals(resourceData, transformed)
    }

    @Test
    fun getAwayTeamDetails() = runBlocking {
        val id = "1234"
        val data = TeamDetailsApiResponse(generateListTeamDetailsApiModel(id))
        val resourceData: Resource<TeamDetailsApiResponse?> = Resource.success(data)

        val liveData: MutableLiveData<Resource<TeamDetailsApiResponse?>> = MutableLiveData()
        liveData.value = resourceData

        Mockito.`when`(repository.loadTeamDetailById( id ))
            .thenReturn(resourceData)

        viewModel.loadAwayTeamDetails(id)

        val transformed = viewModel.awayTeamDetails.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        assertEquals(resourceData, transformed)
    }

    @Test
    fun getHomeTeamDetails() = runBlocking {
        val id = "1234"
        val data = TeamDetailsApiResponse(generateListTeamDetailsApiModel(id))
        val resourceData: Resource<TeamDetailsApiResponse?> = Resource.success(data)

        val liveData: MutableLiveData<Resource<TeamDetailsApiResponse?>> = MutableLiveData()
        liveData.value = resourceData

        Mockito.`when`(repository.loadTeamDetailById( id ))
            .thenReturn(resourceData)

        viewModel.loadHomeTeamDetails(id)

        val transformed = viewModel.homeTeamDetails.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        assertEquals(resourceData, transformed)
    }
}