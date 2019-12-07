package com.indramahkota.footballmatchschedule.ui.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.locale.entity.LeagueEntity
import com.indramahkota.footballmatchschedule.ui.activity.main.adapter.LeagueAdapter
import com.indramahkota.footballmatchschedule.ui.activity.match.MatchActivity
import com.indramahkota.footballmatchschedule.ui.activity.match.MatchActivity.Companion.PARCELABLE_LEAGUE_DATA
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            lparams(width = matchParent, height = wrapContent)
            recyclerView {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(DividerItemDecoration(context, 1))
                adapter = LeagueAdapter(getData()) {
                    startActivity(intentFor<MatchActivity>(PARCELABLE_LEAGUE_DATA to it))
                }
            }
        }
    }

    private fun getData() : List<LeagueEntity> {
        val items: MutableList<LeagueEntity> = mutableListOf()
        val idLeague = resources.getStringArray(R.array.league_id)
        val nameLeague = resources.getStringArray(R.array.league_name)
        val imageLeague = resources.getStringArray(R.array.league_image)
        for (i in idLeague.indices) items.add(
            LeagueEntity( idLeague[i], nameLeague[i], imageLeague[i] )
        )
        return items
    }
}
