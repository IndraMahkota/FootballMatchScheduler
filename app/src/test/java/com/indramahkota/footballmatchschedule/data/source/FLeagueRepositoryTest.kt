package com.indramahkota.footballmatchschedule.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.indramahkota.footballmatchschedule.FakeData.generateListLeagueDetailsApiModel
import com.indramahkota.footballmatchschedule.FakeData.generateListMatchDetailsApiModel
import com.indramahkota.footballmatchschedule.FakeData.generateListTeamDetailsApiModel
import com.indramahkota.footballmatchschedule.MainCoroutineRule
import com.indramahkota.footballmatchschedule.data.source.locale.database.MyDatabase
import com.indramahkota.footballmatchschedule.data.source.remote.api.ApiEndPoint
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.TeamDetailsApiResponse
import com.indramahkota.footballmatchschedule.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class FLeagueRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val api: ApiEndPoint = mock()
    private val db: MyDatabase = mock()
    private val repository: FakeFLeagueRepository = FakeFLeagueRepository(api, db)

    @Test
    fun loadLeagueDetailsByLeagueId() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val leagueDetails = LeagueDetailsApiResponse(generateListLeagueDetailsApiModel())
        val leagueDetailsResource: Resource<LeagueDetailsApiResponse?> = Resource.success(leagueDetails)

        Mockito.`when`(api.getLeagueDetailsByLeagueId(id))
            .thenReturn(leagueDetails)

        Assert.assertEquals(leagueDetailsResource.data?.leagues, repository.loadLeagueDetailsByLeagueId(id).data?.leagues)
    }

    @Test
    fun loadMatchDetailById() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val matchDetail = MatchDetailsApiResponse(generateListMatchDetailsApiModel())
        val matchDetailResource: Resource<MatchDetailsApiResponse?> = Resource.success(matchDetail)

        Mockito.`when`(api.getMatchDetailById(id))
            .thenReturn(matchDetail)

        Assert.assertEquals(matchDetailResource.data?.events, repository.loadMatchDetailById(id).data?.events)
    }

    @Test
    fun loadTeamDetailById() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val teamDetail = TeamDetailsApiResponse(generateListTeamDetailsApiModel())
        val teamDetailResource: Resource<TeamDetailsApiResponse?> = Resource.success(teamDetail)

        Mockito.`when`(api.getTeamDetailById(id))
            .thenReturn(teamDetail)

        Assert.assertEquals(teamDetailResource.data?.teams, repository.loadTeamDetailById(id).data?.teams)
    }

    @Test
    fun loadAllTeamByLeagueId() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val teamDetail = TeamDetailsApiResponse(generateListTeamDetailsApiModel())
        val teamDetailResource: Resource<TeamDetailsApiResponse?> = Resource.success(teamDetail)

        Mockito.`when`(api.getAllTeamByLeagueId(id))
            .thenReturn(teamDetail)

        Assert.assertEquals(teamDetailResource.data?.teams, repository.loadAllTeamByLeagueId(id).data?.teams)
    }

    @Test
    fun loadNextMatchesByLeagueId() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val matchDetail = MatchDetailsApiResponse(generateListMatchDetailsApiModel())
        val matchDetailResource: Resource<MatchDetailsApiResponse?> = Resource.success(matchDetail)

        Mockito.`when`(api.getNextMatchesByLeagueId(id))
            .thenReturn(matchDetail)

        Assert.assertEquals(matchDetailResource.data?.events, repository.loadNextMatchesByLeagueId(id).data?.events)
    }

    @Test
    fun loadLastMatchesByLeagueId() = mainCoroutineRule.runBlockingTest {
        val id = "123"
        val matchDetail = MatchDetailsApiResponse(generateListMatchDetailsApiModel())
        val matchDetailResource: Resource<MatchDetailsApiResponse?> = Resource.success(matchDetail)

        Mockito.`when`(api.getLastMatchesByLeagueId(id))
            .thenReturn(matchDetail)

        Assert.assertEquals(matchDetailResource.data?.events, repository.loadLastMatchesByLeagueId(id).data?.events)
    }
}