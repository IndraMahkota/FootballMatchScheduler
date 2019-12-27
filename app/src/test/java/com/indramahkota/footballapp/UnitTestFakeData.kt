package com.indramahkota.footballapp

import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity
import com.indramahkota.footballapp.data.source.remote.apimodel.ClassementApiModel
import com.indramahkota.footballapp.data.source.remote.apimodel.LeagueDetailsApiModel
import com.indramahkota.footballapp.data.source.remote.apimodel.MatchDetailsApiModel
import com.indramahkota.footballapp.data.source.remote.apimodel.TeamDetailsApiModel
import com.indramahkota.footballapp.utilities.toTeamEntity

object UnitTestFakeData {
    fun generateMatchEntity(id: String): MatchEntity{
        return MatchEntity(id,
            "this_id_home_team_test", "this_id_away_team_test",
            "this_date_event_test","this_home_team_test",
            "this_away_team_test","this_home_score_test",
            "this_away_score_test", "this_image_test",
            "this_image_test")
    }

    fun generateListMatchEntity(id: String): List<MatchEntity>{
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

    fun generateListTeamDetailsApiModel(): List<TeamDetailsApiModel> {
        return mutableListOf(
            generateTeamDetailsApiModel("this_id_home_team_test"),
            generateTeamDetailsApiModel("this_id_away_team_test")
        )
    }

    fun generateListTeamEntity(): List<TeamEntity> {
        return mutableListOf(
            generateTeamDetailsApiModel("this_id_home_team_test").toTeamEntity(),
            generateTeamDetailsApiModel("this_id_away_team_test").toTeamEntity()
        )
    }

    private fun generateClassementApiModel(): ClassementApiModel {
        return ClassementApiModel("", "", "", "",
            "", "", "", "", "", "", "")
    }

    fun generateListClassementApiModel(): List<ClassementApiModel> {
        return mutableListOf(
            generateClassementApiModel()
        )
    }
}