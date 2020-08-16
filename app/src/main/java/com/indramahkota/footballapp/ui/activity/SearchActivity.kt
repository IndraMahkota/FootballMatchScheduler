package com.indramahkota.footballapp.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity
import com.indramahkota.footballapp.ui.activity.DetailsMatchActivity.Companion.PARCELABLE_MATCH_DATA
import com.indramahkota.footballapp.ui.activity.DetailsTeamActivity.Companion.PARCELABLE_TEAM_DATA
import com.indramahkota.footballapp.ui.adapter.MatchVerticalAdapter
import com.indramahkota.footballapp.ui.adapter.TeamAdapter
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*

class SearchActivity : AppCompatActivity() {

    companion object {
        const val MATCH_PARCELABLE_DATA = "match_parcelable_data"
        const val TEAM_PARCELABLE_DATA = "team_parcelable_data"
    }

    private var searchType = 0
    private var searchQuery = ""

    private lateinit var matchAdapter: MatchVerticalAdapter
    private lateinit var teamAdapter: TeamAdapter

    private var listMatchData: List<MatchEntity>? = null
    private var listTeamData: List<TeamEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        shimmer_view_container.visibility = View.GONE

        listMatchData = intent?.getParcelableArrayListExtra(MATCH_PARCELABLE_DATA)
        listTeamData = intent?.getParcelableArrayListExtra(TEAM_PARCELABLE_DATA)

        rv_match.setHasFixedSize(true)
        rv_team.setHasFixedSize(true)

        val rvMatchData = mutableListOf<MatchEntity>()
        matchAdapter = MatchVerticalAdapter(rvMatchData) { matchModel ->
            run {
                val intent = Intent(this, DetailsMatchActivity::class.java).apply {
                    putExtra(PARCELABLE_MATCH_DATA, matchModel)
                }
                startActivity(intent)
            }
        }
        rv_match.adapter = matchAdapter

        val rvTeamData = mutableListOf<TeamEntity>()
        teamAdapter = TeamAdapter(rvTeamData) { teamModel ->
            run {
                val intent = Intent(this, DetailsTeamActivity::class.java).apply {
                    putExtra(PARCELABLE_TEAM_DATA, teamModel)
                }
                startActivity(intent)
            }
        }
        rv_team.adapter = teamAdapter

        setRecycleView(null, searchType)
    }

    private fun setRecycleView(query: String? = null, type: Int) {
        var newMatchData: List<MatchEntity>? = null
        var newTeamData: List<TeamEntity>? = null

        if (query != null && query.isNotEmpty()) {
            newMatchData = listMatchData?.filter {
                it.strHomeTeam.toLowerCase(Locale.getDefault()).contains(query) ||
                        it.strAwayTeam.toLowerCase(Locale.getDefault()).contains(query)
            }

            newTeamData = listTeamData?.filter {
                it.strTeam.toLowerCase(Locale.getDefault()).contains(query)
            }
        }

        if (newTeamData != null) {
            teamAdapter.replace(newTeamData)
        } else {
            teamAdapter.removeAllData()
        }

        if (newMatchData != null) {
            matchAdapter.replace(newMatchData)
        } else {
            matchAdapter.removeAllData()
        }

        if (type == 0) {
            if (newMatchData.isNullOrEmpty()) {
                no_data.visibility = View.VISIBLE
            } else {
                no_data.visibility = View.INVISIBLE
            }

            rv_match.visibility = View.VISIBLE
            rv_team.visibility = View.GONE
        } else {
            if (newTeamData.isNullOrEmpty()) {
                no_data.visibility = View.VISIBLE
            } else {
                no_data.visibility = View.INVISIBLE
            }

            rv_team.visibility = View.VISIBLE
            rv_match.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu?.findItem(R.id.search_menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.isFocusable = true
        searchView.isIconified = false
        searchView.requestFocusFromTouch()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchQuery = query
                setRecycleView(query.toLowerCase(Locale.getDefault()), searchType)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                searchQuery = query
                setRecycleView(query.toLowerCase(Locale.getDefault()), searchType)
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.search_match -> {
                searchType = 0
                setRecycleView(searchQuery, searchType)
                true
            }
            R.id.search_team -> {
                searchType = 1
                setRecycleView(searchQuery, searchType)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
