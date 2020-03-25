package com.indramahkota.footballapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.indramahkota.footballapp.MainCoroutineRule
import com.indramahkota.footballapp.UnitTestFakeData.generateListClassementApiModel
import com.indramahkota.footballapp.UnitTestFakeData.generateListLeagueDetailsApiModel
import com.indramahkota.footballapp.UnitTestFakeData.generateListMatchDetailsApiModel
import com.indramahkota.footballapp.UnitTestFakeData.generateListMatchEntity
import com.indramahkota.footballapp.UnitTestFakeData.generateListTeamDetailsApiModel
import com.indramahkota.footballapp.data.source.FootballAppRepository
import com.indramahkota.footballapp.data.source.Resource
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.data.source.remote.model.ClassementResponse
import com.indramahkota.footballapp.data.source.remote.model.LeagueDetailsResponse
import com.indramahkota.footballapp.data.source.remote.model.MatchDetailsResponse
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
class LeagueDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val repository: FootballAppRepository = mock()

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

        val allTeamData =
            TeamDetailsResponse(
                generateListTeamDetailsApiModel()
            )
        val allTeamResource: Resource<TeamDetailsResponse?> = Resource.success(allTeamData)

        val nextMatchData =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val nextMatchDataResource: Resource<MatchDetailsResponse?> = Resource.success(nextMatchData)

        val prevMatchData =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val prevMatchDataResource: Resource<MatchDetailsResponse?> = Resource.success(prevMatchData)

        val classementData =
            ClassementResponse(
                generateListClassementApiModel()
            )
        val classementDataResource: Resource<ClassementResponse?> = Resource.success(classementData)

        Mockito.`when`(repository.loadAllTeamByLeagueId( id ))
            .thenReturn(allTeamResource)

        Mockito.`when`(repository.loadNextMatchesByLeagueId( id ))
            .thenReturn(nextMatchDataResource)

        Mockito.`when`(repository.loadLastMatchesByLeagueId( id ))
            .thenReturn(prevMatchDataResource)

        Mockito.`when`(repository.loadClassementByLeagueId( id ))
            .thenReturn(classementDataResource)

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

        val allTeamData =
            TeamDetailsResponse(
                generateListTeamDetailsApiModel()
            )
        val allTeamResource: Resource<TeamDetailsResponse?> = Resource.success(allTeamData)

        val nextMatchData =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val nextMatchDataResource: Resource<MatchDetailsResponse?> = Resource.success(nextMatchData)

        val prevMatchData =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val prevMatchDataResource: Resource<MatchDetailsResponse?> = Resource.success(prevMatchData)

        val classementData =
            ClassementResponse(
                generateListClassementApiModel()
            )
        val classementDataResource: Resource<ClassementResponse?> = Resource.success(classementData)

        Mockito.`when`(repository.loadAllTeamByLeagueId( id ))
            .thenReturn(allTeamResource)

        Mockito.`when`(repository.loadNextMatchesByLeagueId( id ))
            .thenReturn(nextMatchDataResource)

        Mockito.`when`(repository.loadLastMatchesByLeagueId( id ))
            .thenReturn(prevMatchDataResource)

        Mockito.`when`(repository.loadClassementByLeagueId( id ))
            .thenReturn(classementDataResource)

        viewModel.loadAllDetails(id)

        val transformed = viewModel.newPrevMatchData.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        Assert.assertEquals(matchList.data?.get(0), transformed.data?.get(0))
    }

    @Test
    fun `Check success value when get League Details by League Id`() = mainCoroutineRule.runBlockingTest {
        val id = "this_id_event_test"
        val data =
            LeagueDetailsResponse(
                generateListLeagueDetailsApiModel()
            )
        val resourceData: Resource<LeagueDetailsResponse?> = Resource.success(data)

        val allTeamData =
            TeamDetailsResponse(
                generateListTeamDetailsApiModel()
            )
        val allTeamResource: Resource<TeamDetailsResponse?> = Resource.success(allTeamData)

        val nextMatchData =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val nextMatchDataResource: Resource<MatchDetailsResponse?> = Resource.success(nextMatchData)

        val prevMatchData =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val prevMatchDataResource: Resource<MatchDetailsResponse?> = Resource.success(prevMatchData)

        val classementData =
            ClassementResponse(
                generateListClassementApiModel()
            )
        val classementDataResource: Resource<ClassementResponse?> = Resource.success(classementData)

        Mockito.`when`(repository.loadAllTeamByLeagueId( id ))
            .thenReturn(allTeamResource)

        Mockito.`when`(repository.loadNextMatchesByLeagueId( id ))
            .thenReturn(nextMatchDataResource)

        Mockito.`when`(repository.loadLastMatchesByLeagueId( id ))
            .thenReturn(prevMatchDataResource)

        Mockito.`when`(repository.loadLeagueDetailsByLeagueId( id ))
            .thenReturn(resourceData)

        Mockito.`when`(repository.loadClassementByLeagueId( id ))
            .thenReturn(classementDataResource)

        viewModel.loadAllDetails(id)

        val transformed = viewModel.leagueDetails.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        Assert.assertEquals(resourceData.data?.leagues, transformed.data?.leagues)
    }

    @Test
    fun `Check value when get All Match Data`() = mainCoroutineRule.runBlockingTest {
        val id = "this_id_event_test"
        val data =
            LeagueDetailsResponse(
                generateListLeagueDetailsApiModel()
            )
        val resourceData: Resource<LeagueDetailsResponse?> = Resource.success(data)

        val allTeamData =
            TeamDetailsResponse(
                generateListTeamDetailsApiModel()
            )
        val allTeamResource: Resource<TeamDetailsResponse?> = Resource.success(allTeamData)

        val nextMatchData =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val nextMatchDataResource: Resource<MatchDetailsResponse?> = Resource.success(nextMatchData)

        val prevMatchData =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val prevMatchDataResource: Resource<MatchDetailsResponse?> = Resource.success(prevMatchData)

        val classementData =
            ClassementResponse(
                generateListClassementApiModel()
            )
        val classementDataResource: Resource<ClassementResponse?> = Resource.success(classementData)

        val listMatchEntity = generateListMatchEntity(id)

        Mockito.`when`(repository.loadAllTeamByLeagueId( id ))
            .thenReturn(allTeamResource)

        Mockito.`when`(repository.loadNextMatchesByLeagueId( id ))
            .thenReturn(nextMatchDataResource)

        Mockito.`when`(repository.loadLastMatchesByLeagueId( id ))
            .thenReturn(prevMatchDataResource)

        Mockito.`when`(repository.loadLeagueDetailsByLeagueId( id ))
            .thenReturn(resourceData)

        Mockito.`when`(repository.loadClassementByLeagueId( id ))
            .thenReturn(classementDataResource)

        viewModel.loadAllDetails(id)

        Assert.assertEquals(listMatchEntity, viewModel.getAllMatchsData())
    }
}