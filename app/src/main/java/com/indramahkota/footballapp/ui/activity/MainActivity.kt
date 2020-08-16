package com.indramahkota.footballapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.data.source.locale.entity.LeagueEntity
import com.indramahkota.footballapp.ui.activity.MatchActivity.Companion.PARCELABLE_LEAGUE_DATA
import com.indramahkota.footballapp.ui.adapter.LeagueAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: GridLayoutManager
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
                val intent = Intent(this, MatchActivity::class.java).apply {
                    putExtra(PARCELABLE_LEAGUE_DATA, leagueModel)
                }
                startActivity(intent)
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
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}