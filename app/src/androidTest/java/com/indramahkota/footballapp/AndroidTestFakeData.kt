package com.indramahkota.footballapp

import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity

object AndroidTestFakeData {
    fun generateMatchEntity(home: String, away: String, homeScore: String, awayScore: String): MatchEntity{
        val url = "https://www.uidownload.com/files/37/743/820/soccer-ball-icon.png"
        return MatchEntity("4328",
            "this_id_home_team_test", "this_id_away_team_test",
            "this_date_event_test",home, away,homeScore, awayScore, url, url)
    }

    fun generateListMatchEntity(): ArrayList<MatchEntity>{
        val arrayListData = ArrayList<MatchEntity>()
        arrayListData.add(generateMatchEntity("Southampton", "West Ham", "2", "3"))
        arrayListData.add(generateMatchEntity("Leicester", "Norwich", "1", "0"))
        arrayListData.add(generateMatchEntity("Liverpool", "Watford", "0", "2"))
        arrayListData.add(generateMatchEntity("Burnley", "Newcastle", "2", "2"))
        arrayListData.add(generateMatchEntity("Sheffield United", "Aston Villa", "1", "1"))
        arrayListData.add(generateMatchEntity("Wolves", "Tottenham", "3", "3"))
        arrayListData.add(generateMatchEntity("Man United", "Everton", "0", "0"))
        arrayListData.add(generateMatchEntity("Crystal Palace", "Brighton", "2", "4"))
        arrayListData.add(generateMatchEntity("Man City", "Leicester", "4", "3"))
        arrayListData.add(generateMatchEntity("Newcastle", "Crystal Palace", "3", "4"))
        return arrayListData
    }

    fun generateTeamEntity(): TeamEntity {
        val url = "https://www.thesportsdb.com/images/media/team/badge/a1af2i1557005128.png"
        return TeamEntity("133604",
            "Arsenal", url,
            "description")
    }
}