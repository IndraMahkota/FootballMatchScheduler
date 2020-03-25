package com.indramahkota.footballapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.indramahkota.footballapp.MainCoroutineRule
import com.indramahkota.footballapp.UnitTestFakeData.generateListTeamDetailsApiModel
import com.indramahkota.footballapp.data.source.FootballAppRepository
import com.indramahkota.footballapp.data.source.Resource
import com.indramahkota.footballapp.data.source.remote.model.TeamDetailsResponse
import com.indramahkota.footballapp.getOrAwaitValue
import com.indramahkota.footballapp.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class TeamDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val repository: FootballAppRepository = mock()

    private lateinit var viewModel: TeamDetailsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = TeamDetailsViewModel(repository)
    }

    @Test
    fun `Check success value when get Match Details by Event Id`() = mainCoroutineRule.runBlockingTest {
        val id = "1234"
        val data =
            TeamDetailsResponse(
                generateListTeamDetailsApiModel()
            )
        val resourceData: Resource<TeamDetailsResponse?> = Resource.success(data)

        Mockito.`when`(repository.loadTeamDetailById( id ))
            .thenReturn(resourceData)

        viewModel.loadTeamDetails(id)

        val transformed = viewModel.teamDetails.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        Assert.assertEquals(resourceData.data?.teams, transformed.data?.teams)
    }

}