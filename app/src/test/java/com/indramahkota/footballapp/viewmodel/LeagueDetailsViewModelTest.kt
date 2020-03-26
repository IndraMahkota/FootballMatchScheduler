package com.indramahkota.footballapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.indramahkota.footballapp.MainCoroutineRule
import com.indramahkota.footballapp.UnitTestFakeData.generateListClassementApiModel
import com.indramahkota.footballapp.UnitTestFakeData.generateListLeagueDetailsApiModel
import com.indramahkota.footballapp.UnitTestFakeData.generateListMatchDetailsApiModel
import com.indramahkota.footballapp.UnitTestFakeData.generateListMatchEntity
import com.indramahkota.footballapp.UnitTestFakeData.generateListTeamDetailsApiModel
import com.indramahkota.footballapp.data.source.repository.FootballAppRepository
import com.indramahkota.footballapp.data.source.repository.Result
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

        val allTeamData =
            TeamDetailsResponse(
                generateListTeamDetailsApiModel()
            )
        val allTeamResult: Result<TeamDetailsResponse?> = Result.Success(allTeamData)

        val nextMatchData =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val nextMatchDataResult: Result<MatchDetailsResponse?> = Result.Success(nextMatchData)

        val prevMatchData =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val prevMatchDataResult: Result<MatchDetailsResponse?> = Result.Success(prevMatchData)

        val classementData =
            ClassementResponse(
                generateListClassementApiModel()
            )
        val classementDataResult: Result<ClassementResponse?> = Result.Success(classementData)

        Mockito.`when`(repository.loadAllTeamByLeagueId( id ))
            .thenReturn(allTeamResult)

        Mockito.`when`(repository.loadNextMatchesByLeagueId( id ))
            .thenReturn(nextMatchDataResult)

        Mockito.`when`(repository.loadLastMatchesByLeagueId( id ))
            .thenReturn(prevMatchDataResult)

        Mockito.`when`(repository.loadClassementByLeagueId( id ))
            .thenReturn(classementDataResult)

        viewModel.loadAllDetails(id)

        val transformed = viewModel.newNextMatchData.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        if(transformed is Result.Success)
            Assert.assertEquals(data[0], transformed.data?.get(0))
    }

    @Test
    fun `Check success value when get Prev Match Data by League Id`() = mainCoroutineRule.runBlockingTest {
        val id = "this_id_event_test"
        val data = generateListMatchEntity(id)

        val allTeamData =
            TeamDetailsResponse(
                generateListTeamDetailsApiModel()
            )
        val allTeamResult: Result<TeamDetailsResponse?> = Result.Success(allTeamData)

        val nextMatchData =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val nextMatchDataResult: Result<MatchDetailsResponse?> = Result.Success(nextMatchData)

        val prevMatchData =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val prevMatchDataResult: Result<MatchDetailsResponse?> = Result.Success(prevMatchData)

        val classementData =
            ClassementResponse(
                generateListClassementApiModel()
            )
        val classementDataResult: Result<ClassementResponse?> = Result.Success(classementData)

        Mockito.`when`(repository.loadAllTeamByLeagueId( id ))
            .thenReturn(allTeamResult)

        Mockito.`when`(repository.loadNextMatchesByLeagueId( id ))
            .thenReturn(nextMatchDataResult)

        Mockito.`when`(repository.loadLastMatchesByLeagueId( id ))
            .thenReturn(prevMatchDataResult)

        Mockito.`when`(repository.loadClassementByLeagueId( id ))
            .thenReturn(classementDataResult)

        viewModel.loadAllDetails(id)

        val transformed = viewModel.newPrevMatchData.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        if(transformed is Result.Success)
            Assert.assertEquals(data[0], transformed.data?.get(0))
    }

    @Test
    fun `Check success value when get League Details by League Id`() = mainCoroutineRule.runBlockingTest {
        val id = "this_id_event_test"
        val data =
            LeagueDetailsResponse(
                generateListLeagueDetailsApiModel()
            )
        val resultData: Result<LeagueDetailsResponse?> = Result.Success(data)

        val allTeamData =
            TeamDetailsResponse(
                generateListTeamDetailsApiModel()
            )
        val allTeamResult: Result<TeamDetailsResponse?> = Result.Success(allTeamData)

        val nextMatchData =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val nextMatchDataResult: Result<MatchDetailsResponse?> = Result.Success(nextMatchData)

        val prevMatchData =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val prevMatchDataResult: Result<MatchDetailsResponse?> = Result.Success(prevMatchData)

        val classementData =
            ClassementResponse(
                generateListClassementApiModel()
            )
        val classementDataResult: Result<ClassementResponse?> = Result.Success(classementData)

        Mockito.`when`(repository.loadAllTeamByLeagueId( id ))
            .thenReturn(allTeamResult)

        Mockito.`when`(repository.loadNextMatchesByLeagueId( id ))
            .thenReturn(nextMatchDataResult)

        Mockito.`when`(repository.loadLastMatchesByLeagueId( id ))
            .thenReturn(prevMatchDataResult)

        Mockito.`when`(repository.loadLeagueDetailsByLeagueId( id ))
            .thenReturn(resultData)

        Mockito.`when`(repository.loadClassementByLeagueId( id ))
            .thenReturn(classementDataResult)

        viewModel.loadAllDetails(id)

        val transformed = viewModel.leagueDetails.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }

        if(transformed is Result.Success)
            Assert.assertEquals(data.leagues, transformed.data?.leagues)
    }

    @Test
    fun `Check value when get All Match Data`() = mainCoroutineRule.runBlockingTest {
        val id = "this_id_event_test"
        val data =
            LeagueDetailsResponse(
                generateListLeagueDetailsApiModel()
            )
        val resultData: Result<LeagueDetailsResponse?> = Result.Success(data)

        val allTeamData =
            TeamDetailsResponse(
                generateListTeamDetailsApiModel()
            )
        val allTeamResult: Result<TeamDetailsResponse?> = Result.Success(allTeamData)

        val nextMatchData =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val nextMatchDataResult: Result<MatchDetailsResponse?> = Result.Success(nextMatchData)

        val prevMatchData =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val prevMatchDataResult: Result<MatchDetailsResponse?> = Result.Success(prevMatchData)

        val classementData =
            ClassementResponse(
                generateListClassementApiModel()
            )
        val classementDataResult: Result<ClassementResponse?> = Result.Success(classementData)

        val listMatchEntity = generateListMatchEntity(id)

        Mockito.`when`(repository.loadAllTeamByLeagueId( id ))
            .thenReturn(allTeamResult)

        Mockito.`when`(repository.loadNextMatchesByLeagueId( id ))
            .thenReturn(nextMatchDataResult)

        Mockito.`when`(repository.loadLastMatchesByLeagueId( id ))
            .thenReturn(prevMatchDataResult)

        Mockito.`when`(repository.loadLeagueDetailsByLeagueId( id ))
            .thenReturn(resultData)

        Mockito.`when`(repository.loadClassementByLeagueId( id ))
            .thenReturn(classementDataResult)

        viewModel.loadAllDetails(id)

        Assert.assertEquals(listMatchEntity, viewModel.getAllMatchsData())
    }
}