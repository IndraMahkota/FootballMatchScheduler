package com.indramahkota.footballmatchschedule.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.indramahkota.footballmatchschedule.data.source.remote.api.ApiEndPoint
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.TeamDetailsApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FLeagueRepository @Inject constructor( private val api: ApiEndPoint ) : FLeagueDataSource {
    override fun loadLeagueDetailsByLeagueId(id: String): LiveData<Resource<LeagueDetailsApiResponse?>> {
        val result: MutableLiveData<Resource<LeagueDetailsApiResponse?>> = MutableLiveData()
        val call: Call<LeagueDetailsApiResponse> = api.getLeagueDetailsByLeagueId(id)
        call.enqueue(object : Callback<LeagueDetailsApiResponse?> {
            override fun onResponse(call: Call<LeagueDetailsApiResponse?>, response: Response<LeagueDetailsApiResponse?> ) {
                if (response.body() != null) {
                    val leagueApiResponse = LeagueDetailsApiResponse(response.body()!!.leagues)
                    result.postValue(Resource.success(leagueApiResponse))
                }
            }

            override fun onFailure(call: Call<LeagueDetailsApiResponse?>, t: Throwable ) {
                result.postValue(Resource.error(t.message, LeagueDetailsApiResponse()))
            }
        })

        return result
    }

    override fun loadMatchDetailById(id: String): LiveData<Resource<MatchDetailsApiResponse?>> {
        val result: MutableLiveData<Resource<MatchDetailsApiResponse?>> = MutableLiveData()
        val call: Call<MatchDetailsApiResponse> = api.getMatchDetailById(id)
        call.enqueue(object : Callback<MatchDetailsApiResponse?> {
            override fun onResponse(call: Call<MatchDetailsApiResponse?>, response: Response<MatchDetailsApiResponse?> ) {
                if (response.body() != null) {
                    val matchApiResponse = MatchDetailsApiResponse(response.body()!!.events)
                    result.postValue(Resource.success(matchApiResponse))
                }
            }

            override fun onFailure(call: Call<MatchDetailsApiResponse?>, t: Throwable ) {
                result.postValue(Resource.error(t.message, MatchDetailsApiResponse()))
            }
        })

        return result
    }

    override fun loadTeamDetailById(id: String): LiveData<Resource<TeamDetailsApiResponse?>> {
        val result: MutableLiveData<Resource<TeamDetailsApiResponse?>> = MutableLiveData()
        val call: Call<TeamDetailsApiResponse> = api.getTeamDetailById(id)
        call.enqueue(object : Callback<TeamDetailsApiResponse?> {
            override fun onResponse(call: Call<TeamDetailsApiResponse?>, response: Response<TeamDetailsApiResponse?> ) {
                if (response.body() != null) {
                    val teamDetailsApiResponse = TeamDetailsApiResponse(response.body()!!.teams)
                    result.postValue(Resource.success(teamDetailsApiResponse))
                }
            }

            override fun onFailure(call: Call<TeamDetailsApiResponse?>, t: Throwable ) {
                result.postValue(Resource.error(t.message, TeamDetailsApiResponse()))
            }
        })

        return result
    }

    override suspend fun loadAllTeamByLeagueId(id: String): Resource<TeamDetailsApiResponse?> {
        return try {
            Resource.success(api.getAllTeamByLeagueId(id))
        } catch (e: Exception) {
            Resource.error(e.message, TeamDetailsApiResponse())
        }
    }

    override suspend fun loadNextMatchesByLeagueId(id: String): Resource<MatchDetailsApiResponse?> {
        return try {
            Resource.success(api.getNextMatchesByLeagueId(id))
        } catch (e: Exception) {
            Resource.error(e.message, MatchDetailsApiResponse())
        }
    }

    override suspend fun loadLastMatchesByLeagueId(id: String): Resource<MatchDetailsApiResponse?> {
        return try {
            Resource.success(api.getLastMatchesByLeagueId(id))
        } catch (e: Exception) {
            Resource.error(e.message, MatchDetailsApiResponse())
        }
    }
}