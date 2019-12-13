package com.indramahkota.footballmatchschedule

import com.indramahkota.footballmatchschedule.data.source.locale.entity.MatchEntity

object AndroidTestFakeData {
    private fun generateMatchEntity(home: String, away: String): MatchEntity{
        return MatchEntity("4328",
            "this_id_home_team_test", "this_id_away_team_test",
            "this_date_event_test",home, away,"this_home_score_test",
            "this_away_score_test", "this_image_test",
            "this_image_test")
    }

    fun generateListMatchEntity(): ArrayList<MatchEntity>{
        val arrayListData = ArrayList<MatchEntity>()
        arrayListData.add(generateMatchEntity("Southampton", "West Ham"))
        arrayListData.add(generateMatchEntity("Leicester", "Norwich"))
        arrayListData.add(generateMatchEntity("Liverpool", "Watford"))
        arrayListData.add(generateMatchEntity("Burnley", "Newcastle"))
        arrayListData.add(generateMatchEntity("Sheffield United", "Aston Villa"))
        arrayListData.add(generateMatchEntity("Wolves", "Tottenham"))
        arrayListData.add(generateMatchEntity("Man United", "Everton"))
        arrayListData.add(generateMatchEntity("Crystal Palace", "Brighton"))
        arrayListData.add(generateMatchEntity("Man City", "Leicester"))
        arrayListData.add(generateMatchEntity("Newcastle", "Crystal Palace"))
        return arrayListData
    }
}