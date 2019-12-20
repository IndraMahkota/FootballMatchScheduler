package com.indramahkota.footballapp.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.data.source.locale.entity.LeagueEntity
import com.indramahkota.footballapp.ui.adapter.LeagueAdapter
import com.indramahkota.footballapp.ui.activity.MatchActivity.Companion.PARCELABLE_LEAGUE_DATA
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var leagueAdapter: LeagueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        linearLayoutManager = GridLayoutManager(this, 2)
        rv_league.layoutManager = linearLayoutManager
        rv_league.setHasFixedSize(true)

        leagueAdapter =
            LeagueAdapter(
                getData()
            ) { leagueModel ->
                startActivity(intentFor<MatchActivity>(PARCELABLE_LEAGUE_DATA to leagueModel))
            }
        rv_league.adapter = leagueAdapter
    }

    private fun getData(): List<LeagueEntity> {
        val items: MutableList<LeagueEntity> = mutableListOf()
        val idLeague = resources.getStringArray(R.array.league_id)
        val nameLeague = resources.getStringArray(R.array.league_name)
        val imageLeague = resources.getStringArray(R.array.league_image)
        for (i in idLeague.indices) items.add(
            LeagueEntity(idLeague[i], nameLeague[i], imageLeague[i])
        )
        return items
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_favorite, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite_menu -> {
                startActivity(intentFor<FavoriteActivity>())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}