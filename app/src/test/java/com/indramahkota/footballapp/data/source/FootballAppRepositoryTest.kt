package com.indramahkota.footballapp.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.indramahkota.footballapp.UnitTestFakeData.generateListLeagueDetailsApiModel
import com.indramahkota.footballapp.UnitTestFakeData.generateListMatchDetailsApiModel
import com.indramahkota.footballapp.UnitTestFakeData.generateListTeamDetailsApiModel
import com.indramahkota.footballapp.MainCoroutineRule
import com.indramahkota.footballapp.data.source.locale.database.MyDatabase
import com.indramahkota.footballapp.data.source.remote.api.ApiEndPoint
import com.indramahkota.footballapp.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballapp.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballapp.data.source.remote.apiresponse.TeamDetailsApiResponse
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

    private val api: ApiEndPoint = mock()
    private val db: MyDatabase = mock()
    private val repository: FakeFootballAppRepository = FakeFootballAppRepository(api, db)

    @Test
    fun `Check success value when get League Details By League Id`() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val leagueDetails = LeagueDetailsApiResponse(generateListLeagueDetailsApiModel())
        val leagueDetailsResource: Resource<LeagueDetailsApiResponse?> = Resource.success(leagueDetails)

        Mockito.`when`(api.getLeagueDetailsByLeagueId(id))
            .thenReturn(leagueDetails)

        Assert.assertEquals(leagueDetailsResource.data?.leagues, repository.loadLeagueDetailsByLeagueId(id).data?.leagues)
    }

    @Test
    fun `Check success value when get Match Details By Event Id`() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val matchDetail = MatchDetailsApiResponse(generateListMatchDetailsApiModel())
        val matchDetailResource: Resource<MatchDetailsApiResponse?> = Resource.success(matchDetail)

        Mockito.`when`(api.getMatchDetailsById(id))
            .thenReturn(matchDetail)

        Assert.assertEquals(matchDetailResource.data?.events, repository.loadMatchDetailById(id).data?.events)
    }

    @Test
    fun `Check success value when get Team Details By Team Id`() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val teamDetail = TeamDetailsApiResponse(generateListTeamDetailsApiModel())
        val teamDetailResource: Resource<TeamDetailsApiResponse?> = Resource.success(teamDetail)

        Mockito.`when`(api.getTeamDetailsById(id))
            .thenReturn(teamDetail)

        Assert.assertEquals(teamDetailResource.data?.teams, repository.loadTeamDetailById(id).data?.teams)
    }

    @Test
    fun `Check success value when get All Team By League Id`() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val teamDetail = TeamDetailsApiResponse(generateListTeamDetailsApiModel())
        val teamDetailResource: Resource<TeamDetailsApiResponse?> = Resource.success(teamDetail)

        Mockito.`when`(api.getAllTeamByLeagueId(id))
            .thenReturn(teamDetail)

        Assert.assertEquals(teamDetailResource.data?.teams, repository.loadAllTeamByLeagueId(id).data?.teams)
    }

    @Test
    fun `Check success value when get Next Match By League Id`() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val matchDetail = MatchDetailsApiResponse(generateListMatchDetailsApiModel())
        val matchDetailResource: Resource<MatchDetailsApiResponse?> = Resource.success(matchDetail)

        Mockito.`when`(api.getNextMatchByLeagueId(id))
            .thenReturn(matchDetail)

        Assert.assertEquals(matchDetailResource.data?.events, repository.loadNextMatchesByLeagueId(id).data?.events)
    }

    @Test
    fun `Check success value when get Last Match By League Id`() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val matchDetail = MatchDetailsApiResponse(generateListMatchDetailsApiModel())
        val matchDetailResource: Resource<MatchDetailsApiResponse?> = Resource.success(matchDetail)

        Mockito.`when`(api.getLastMatchByLeagueId(id))
            .thenReturn(matchDetail)

        Assert.assertEquals(matchDetailResource.data?.events, repository.loadLastMatchesByLeagueId(id).data?.events)
    }
}