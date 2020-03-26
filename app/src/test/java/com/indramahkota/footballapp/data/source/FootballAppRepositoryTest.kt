package com.indramahkota.footballapp.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.indramahkota.footballapp.MainCoroutineRule
import com.indramahkota.footballapp.UnitTestFakeData.generateListLeagueDetailsApiModel
import com.indramahkota.footballapp.UnitTestFakeData.generateListMatchDetailsApiModel
import com.indramahkota.footballapp.UnitTestFakeData.generateListTeamDetailsApiModel
import com.indramahkota.footballapp.data.source.locale.database.AppDao
import com.indramahkota.footballapp.data.source.remote.EndPointService
import com.indramahkota.footballapp.data.source.remote.model.LeagueDetailsResponse
import com.indramahkota.footballapp.data.source.remote.model.MatchDetailsResponse
import com.indramahkota.footballapp.data.source.remote.model.TeamDetailsResponse
import com.indramahkota.footballapp.data.source.repository.Result
import com.indramahkota.footballapp.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class FootballAppRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val service: EndPointService = mock()
    private val dao: AppDao = mock()
    private val repository: FakeFootballAppRepository = FakeFootballAppRepository(service, dao)

    @Test
    fun `Check success value when get League Details By League Id`() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val leagueDetails =
            LeagueDetailsResponse(
                generateListLeagueDetailsApiModel()
            )

        Mockito.`when`(service.getLeagueDetailsByLeagueId(id))
            .thenReturn(leagueDetails)

        when(val resource = repository.loadLeagueDetailsByLeagueId(id)) {
            is Result.Success -> Assert.assertEquals(leagueDetails.leagues, resource.data?.leagues)
        }
    }

    @Test
    fun `Check success value when get Match Details By Event Id`() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val matchDetail =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )

        Mockito.`when`(service.getMatchDetailsById(id))
            .thenReturn(matchDetail)

        when (val resource = repository.loadMatchDetailById(id)) {
            is Result.Success -> Assert.assertEquals(matchDetail.events, resource.data?.events)
        }
    }

    @Test
    fun `Check success value when get Team Details By Team Id`() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val teamDetail =
            TeamDetailsResponse(
                generateListTeamDetailsApiModel()
            )

        Mockito.`when`(service.getTeamDetailsById(id))
            .thenReturn(teamDetail)

        when (val resource = repository.loadTeamDetailById(id)) {
            is Result.Success -> Assert.assertEquals(teamDetail.teams, resource.data?.teams)
        }
    }

    @Test
    fun `Check success value when get All Team By League Id`() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val teamDetail =
            TeamDetailsResponse(
                generateListTeamDetailsApiModel()
            )

        Mockito.`when`(service.getAllTeamByLeagueId(id))
            .thenReturn(teamDetail)

        when(val resource = repository.loadAllTeamByLeagueId(id)) {
            is Result.Success -> Assert.assertEquals(teamDetail.teams, resource.data?.teams)
        }
    }

    @Test
    fun `Check success value when get Next Match By League Id`() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val matchDetail =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )

        Mockito.`when`(service.getNextMatchByLeagueId(id))
            .thenReturn(matchDetail)

        when(val resource = repository.loadNextMatchesByLeagueId(id)) {
            is Result.Success -> Assert.assertEquals(matchDetail.events, resource.data?.events)
        }
    }

    @Test
    fun `Check success value when get Last Match By League Id`() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val matchDetail =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )

        Mockito.`when`(service.getLastMatchByLeagueId(id))
            .thenReturn(matchDetail)

        when(val resource = repository.loadLastMatchesByLeagueId(id)) {
            is Result.Success -> Assert.assertEquals(matchDetail.events, resource.data?.events)
        }
    }
}