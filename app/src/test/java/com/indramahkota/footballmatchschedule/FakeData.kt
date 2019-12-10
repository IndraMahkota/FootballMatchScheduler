package com.indramahkota.footballmatchschedule

import com.indramahkota.footballmatchschedule.data.source.locale.entity.MatchEntity
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.LeagueDetailsApiModel
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.MatchDetailsApiModel
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.TeamDetailsApiModel

object FakeData {
    fun generateMatchEntity(id: String):MatchEntity{
        return MatchEntity(id,
            "123", "234",
            "2019-12-12","Liverpool",
            "Barcelona","2",
            "1", "https://www.picture.com/pict1",
            "https://www.picture.com/pict2")
    }

    fun generateListMatchEntity(id: String):List<MatchEntity>{
        return mutableListOf(
            generateMatchEntity(id)
        )
    }

    fun generateLeagueDetailsApiModel(): LeagueDetailsApiModel {
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

    fun generateMatchDetailsApiModel(): MatchDetailsApiModel {
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

    fun generateTeamDetailsApiModel(id: String): TeamDetailsApiModel {
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

    fun generateListTeamDetailsApiModel(id: String): List<TeamDetailsApiModel> {
        return mutableListOf(
            generateTeamDetailsApiModel(id)
        )
    }

}