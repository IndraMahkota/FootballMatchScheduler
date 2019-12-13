package com.indramahkota.footballmatchschedule.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.indramahkota.footballmatchschedule.UnitTestFakeData.generateListLeagueDetailsApiModel
import com.indramahkota.footballmatchschedule.UnitTestFakeData.generateListMatchDetailsApiModel
import com.indramahkota.footballmatchschedule.UnitTestFakeData.generateListMatchEntity
import com.indramahkota.footballmatchschedule.UnitTestFakeData.generateListTeamDetailsApiModel
import com.indramahkota.footballmatchschedule.MainCoroutineRule
import com.indramahkota.footballmatchschedule.data.source.FLeagueRepository
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.locale.entity.MatchEntity
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.TeamDetailsApiResponse
import com.indramahkota.footballmatchschedule.getOrAwaitValue
import com.indramahkota.footballmatchschedule.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class LeagueDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val repository: FLeagueRepository = mock()

    private lateinit var viewModel: LeagueDetailsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = LeagueDetailsViewModel(repository)
    }

    @Test
    fun `Check success value when get Next Match Data by League Id`() = mainCoroutineRule.runBlockingTest {
        val id = "this_id_event_test"
        val data = generateListMatchEntity(id)
        val matchList: Resource<List<MatchEntity>?> = Resource.success(data)

        val allTeamData = TeamDetailsApiResponse(generateListTeamDetailsApiModel())
        val allTeamResource: Resource<TeamDetailsApiResponse?> = Resource.success(allTeamData)

        val nextMatchDataData = MatchDetailsApiResponse(generateListMatchDetailsApiModel())
        val nextMatchDataResource: Resource<MatchDetailsApiResponse?> = Resource.success(nextMatchDataData)

        val prevMatchDataData = MatchDetailsApiResponse(generateListMatchDetailsApiModel())
        val prevMatchDataResource: Resource<MatchDetailsApiResponse?> = Resource.success(prevMatchDataData)

        Mockito.`when`(repository.loadAllTeamByLeagueId( id ))
            .thenReturn(allTeamResource)

        Mockito.`when`(repository.loadNextMatchesByLeagueId( id ))
            .thenReturn(nextMatchDataResource)

        Mockito.`when`(repository.loadLastMatchesByLeagueId( id ))
            .thenReturn(prevMatchDataResource)

        viewModel.loadAllDetails(id)

        val transformed = viewModel.newNextMatchData.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        Assert.assertEquals(matchList.data?.get(0), transformed.data?.get(0))
    }

    @Test
    fun `Check success value when get Prev Match Data by League Id`() = mainCoroutineRule.runBlockingTest {
        val id = "this_id_event_test"
        val data = generateListMatchEntity(id)
        val matchList: Resource<List<MatchEntity>?> = Resource.success(data)

        val allTeamData = TeamDetailsApiResponse(generateListTeamDetailsApiModel())
        val allTeamResource: Resource<TeamDetailsApiResponse?> = Resource.success(allTeamData)

        val nextMatchDataData = MatchDetailsApiResponse(generateListMatchDetailsApiModel())
        val nextMatchDataResource: Resource<MatchDetailsApiResponse?> = Resource.success(nextMatchDataData)

        val prevMatchDataData = MatchDetailsApiResponse(generateListMatchDetailsApiModel())
        val prevMatchDataResource: Resource<MatchDetailsApiResponse?> = Resource.success(prevMatchDataData)

        Mockito.`when`(repository.loadAllTeamByLeagueId( id ))
            .thenReturn(allTeamResource)

        Mockito.`when`(repository.loadNextMatchesByLeagueId( id ))
            .thenReturn(nextMatchDataResource)

        Mockito.`when`(repository.loadLastMatchesByLeagueId( id ))
            .thenReturn(prevMatchDataResource)

        viewModel.loadAllDetails(id)

        val transformed = viewModel.newPrevMatchData.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        Assert.assertEquals(matchList.data?.get(0), transformed.data?.get(0))
    }

    @Test
    fun `Check success value when get League Details by League Id`() = mainCoroutineRule.runBlockingTest {
        val id = "this_id_event_test"
        val data = LeagueDetailsApiResponse(generateListLeagueDetailsApiModel())
        val resourceData: Resource<LeagueDetailsApiResponse?> = Resource.success(data)

        val allTeamData = TeamDetailsApiResponse(generateListTeamDetailsApiModel())
        val allTeamResource: Resource<TeamDetailsApiResponse?> = Resource.success(allTeamData)

        val nextMatchDataData = MatchDetailsApiResponse(generateListMatchDetailsApiModel())
        val nextMatchDataResource: Resource<MatchDetailsApiResponse?> = Resource.success(nextMatchDataData)

        val prevMatchDataData = MatchDetailsApiResponse(generateListMatchDetailsApiModel())
        val prevMatchDataResource: Resource<MatchDetailsApiResponse?> = Resource.success(prevMatchDataData)

        Mockito.`when`(repository.loadAllTeamByLeagueId( id ))
            .thenReturn(allTeamResource)

        Mockito.`when`(repository.loadNextMatchesByLeagueId( id ))
            .thenReturn(nextMatchDataResource)

        Mockito.`when`(repository.loadLastMatchesByLeagueId( id ))
            .thenReturn(prevMatchDataResource)

        Mockito.`when`(repository.loadLeagueDetailsByLeagueId( id ))
            .thenReturn(resourceData)

        viewModel.loadAllDetails(id)

        val transformed = viewModel.leagueDetails.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        Assert.assertEquals(resourceData.data?.leagues, transformed.data?.leagues)
    }

    @Test
    fun `Check value when get All Match Data`() = mainCoroutineRule.runBlockingTest {
        val id = "this_id_event_test"
        val data = LeagueDetailsApiResponse(generateListLeagueDetailsApiModel())
        val resourceData: Resource<LeagueDetailsApiResponse?> = Resource.success(data)

        val allTeamData = TeamDetailsApiResponse(generateListTeamDetailsApiModel())
        val allTeamResource: Resource<TeamDetailsApiResponse?> = Resource.success(allTeamData)

        val nextMatchDataData = MatchDetailsApiResponse(generateListMatchDetailsApiModel())
        val nextMatchDataResource: Resource<MatchDetailsApiResponse?> = Resource.success(nextMatchDataData)

        val prevMatchDataData = MatchDetailsApiResponse(generateListMatchDetailsApiModel())
        val prevMatchDataResource: Resource<MatchDetailsApiResponse?> = Resource.success(prevMatchDataData)

        val listMatchEntity = generateListMatchEntity(id)

        Mockito.`when`(repository.loadAllTeamByLeagueId( id ))
            .thenReturn(allTeamResource)

        Mockito.`when`(repository.loadNextMatchesByLeagueId( id ))
            .thenReturn(nextMatchDataResource)

        Mockito.`when`(repository.loadLastMatchesByLeagueId( id ))
            .thenReturn(prevMatchDataResource)

        Mockito.`when`(repository.loadLeagueDetailsByLeagueId( id ))
            .thenReturn(resourceData)

        viewModel.loadAllDetails(id)

        Assert.assertEquals(listMatchEntity, viewModel.getAllMatchsData())
    }
}