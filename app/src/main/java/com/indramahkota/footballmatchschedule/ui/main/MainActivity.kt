package com.indramahkota.footballmatchschedule.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.LeagueApiModel
import com.indramahkota.footballmatchschedule.ui.main.adapter.LeagueAdapter
import com.indramahkota.footballmatchschedule.ui.match.MatchActivity
import com.indramahkota.footballmatchschedule.ui.match.MatchActivity.Companion.PARCELABLE_LEAGUE_DATA
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: LeagueAdapter
    private lateinit var leagueList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            lparams(width = matchParent, height = wrapContent)
            leagueList = recyclerView {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(DividerItemDecoration(context, 1))
            }
        }

        adapter = LeagueAdapter(getData()) { leagueApiModel ->
            startActivity<MatchActivity>(PARCELABLE_LEAGUE_DATA to leagueApiModel)
        }
        leagueList.adapter = adapter
    }

    private fun getData() : List<LeagueApiModel> {
        val items: MutableList<LeagueApiModel> = mutableListOf()
        val idLeague = resources.getStringArray(R.array.league_id)
        val nameLeague = resources.getStringArray(R.array.league_name)
        val imageLeague = resources.getStringArray(R.array.league_image)

        for (i in idLeague.indices) {
            items.add(LeagueApiModel(idLeague[i], nameLeague[i], imageLeague[i]))
        }
        return items
    }
}
