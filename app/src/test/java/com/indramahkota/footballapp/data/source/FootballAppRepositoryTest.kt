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
        val leagueDetailsResource: Resource<LeagueDetailsResponse?> = Resource.success(leagueDetails)

        Mockito.`when`(service.getLeagueDetailsByLeagueId(id))
            .thenReturn(leagueDetails)

        Assert.assertEquals(leagueDetailsResource.data?.leagues, repository.loadLeagueDetailsByLeagueId(id).data?.leagues)
    }

    @Test
    fun `Check success value when get Match Details By Event Id`() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val matchDetail =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val matchDetailResource: Resource<MatchDetailsResponse?> = Resource.success(matchDetail)

        Mockito.`when`(service.getMatchDetailsById(id))
            .thenReturn(matchDetail)

        Assert.assertEquals(matchDetailResource.data?.events, repository.loadMatchDetailById(id).data?.events)
    }

    @Test
    fun `Check success value when get Team Details By Team Id`() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val teamDetail =
            TeamDetailsResponse(
                generateListTeamDetailsApiModel()
            )
        val teamDetailResource: Resource<TeamDetailsResponse?> = Resource.success(teamDetail)

        Mockito.`when`(service.getTeamDetailsById(id))
            .thenReturn(teamDetail)

        Assert.assertEquals(teamDetailResource.data?.teams, repository.loadTeamDetailById(id).data?.teams)
    }

    @Test
    fun `Check success value when get All Team By League Id`() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val teamDetail =
            TeamDetailsResponse(
                generateListTeamDetailsApiModel()
            )
        val teamDetailResource: Resource<TeamDetailsResponse?> = Resource.success(teamDetail)

        Mockito.`when`(service.getAllTeamByLeagueId(id))
            .thenReturn(teamDetail)

        Assert.assertEquals(teamDetailResource.data?.teams, repository.loadAllTeamByLeagueId(id).data?.teams)
    }

    @Test
    fun `Check success value when get Next Match By League Id`() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val matchDetail =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val matchDetailResource: Resource<MatchDetailsResponse?> = Resource.success(matchDetail)

        Mockito.`when`(service.getNextMatchByLeagueId(id))
            .thenReturn(matchDetail)

        Assert.assertEquals(matchDetailResource.data?.events, repository.loadNextMatchesByLeagueId(id).data?.events)
    }

    @Test
    fun `Check success value when get Last Match By League Id`() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val matchDetail =
            MatchDetailsResponse(
                generateListMatchDetailsApiModel()
            )
        val matchDetailResource: Resource<MatchDetailsResponse?> = Resource.success(matchDetail)

        Mockito.`when`(service.getLastMatchByLeagueId(id))
            .thenReturn(matchDetail)

        Assert.assertEquals(matchDetailResource.data?.events, repository.loadLastMatchesByLeagueId(id).data?.events)
    }
}