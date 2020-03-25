package com.indramahkota.footballapp

import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity
import com.indramahkota.footballapp.data.source.remote.model.ClassementModel
import com.indramahkota.footballapp.data.source.remote.model.LeagueDetailsModel
import com.indramahkota.footballapp.data.source.remote.model.MatchDetailsModel
import com.indramahkota.footballapp.data.source.remote.model.TeamDetailsiModel
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

    private fun generateLeagueDetailsApiModel(): LeagueDetailsModel {
        return LeagueDetailsModel("2019-12-12", "123",
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

    fun generateListLeagueDetailsApiModel(): List<LeagueDetailsModel> {
        return mutableListOf(
            generateLeagueDetailsApiModel()
        )
    }

    private fun generateMatchDetailsApiModel(): MatchDetailsModel {
        return MatchDetailsModel("this_date_event_test", "2019-12-13",
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

    fun generateListMatchDetailsApiModel(): List<MatchDetailsModel>? {
        return mutableListOf(
            generateMatchDetailsApiModel()
        )
    }

    fun generateTeamDetailsApiModel(id: String): TeamDetailsiModel {
        return TeamDetailsiModel("123", "456",
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

    fun generateListTeamDetailsApiModel(): List<TeamDetailsiModel> {
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

    private fun generateClassementApiModel(): ClassementModel {
        return ClassementModel("", "", "", "",
            "", "", "", "", "", "", "")
    }

    fun generateListClassementApiModel(): List<ClassementModel> {
        return mutableListOf(
            generateClassementApiModel()
        )
    }
}