package com.indramahkota.footballmatchschedule

import com.indramahkota.footballmatchschedule.data.source.locale.entity.MatchEntity
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.LeagueDetailsApiModel
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.MatchDetailsApiModel
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.TeamDetailsApiModel

object FakeData {
    fun generateMatchEntity(id: String):MatchEntity{
        return MatchEntity(id,
            "this_id_home_team_test", "this_id_away_team_test",
            "this_date_event_test","this_home_team_test",
            "this_away_team_test","this_home_score_test",
            "this_away_score_test", "this_image_test",
            "this_image_test")
    }

    fun generateListMatchEntity(id: String):List<MatchEntity>{
        return mutableListOf(
            generateMatchEntity(id),
            generateMatchEntity(id)
        )
    }

    private fun generateLeagueDetailsApiModel(): LeagueDetailsApiModel {
        return LeagueDetailsApiModel("2019-12-12", "123",
            "456", "789", "2019", "",
            "", "", "", "",
            "", "", "",
            "", "", "", "",
            "", "", "", "",
            "", "", "", "",
            "", "", "", "",
            "", "", "", "",
            "", "", "", "",
            "", "", "", "",
            "", "")
    }

    fun generateListLeagueDetailsApiModel(): List<LeagueDetailsApiModel> {
        return mutableListOf(
            generateLeagueDetailsApiModel()
        )
    }

    private fun generateMatchDetailsApiModel(): MatchDetailsApiModel {
        return MatchDetailsApiModel("this_date_event_test", "2019-12-13",
            "this_id_away_team_test", "this_id_event_test",
            "this_id_home_team_test", "", "",
            "this_away_score_test", "", "this_home_score_test",
            "", "", "", "",
            "", "", "",
            "", "", "",
            "", "this_away_team_test", "", "",
            "", "", "", "", "",
            "", "", "", "", "",
            "", "", "",
            "", "", "",
            "", "this_home_team_test", "","",
            "", "", "", "", "",
            "", "", "", "", "",
            "", "", "", "")
    }

    fun generateListMatchDetailsApiModel(): List<MatchDetailsApiModel>? {
        return mutableListOf(
            generateMatchDetailsApiModel()
        )
    }

    private fun generateTeamDetailsApiModel(id: String): TeamDetailsApiModel {
        return TeamDetailsApiModel("123", "456",
            id, "2019", "222", "",
            "", "", "", "",
            "", "", "", "",
            "", "", "", "",
            "", "", "", "",
            "", "", "", "",
            "", "", "", "",
            "", "", "", "", "",
            "", "", "", "this_image_test",
            "", "", "", "",
            "", "", "", "",
            "", "", "")
    }

    fun generateListTeamDetailsApiModel(): List<TeamDetailsApiModel> {
        return mutableListOf(
            generateTeamDetailsApiModel("this_id_home_team_test"),
            generateTeamDetailsApiModel("this_id_away_team_test")
        )
    }
}