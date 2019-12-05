package com.indramahkota.footballmatchschedule.ui.match

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.tabs.TabLayout
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.Status
import com.indramahkota.footballmatchschedule.data.source.remote.model.LeagueModel
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.MatchDetailsApiModel
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.TeamDetailsApiModel
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.TeamDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.model.MatchModel
import com.indramahkota.footballmatchschedule.ui.match.adapter.TabPagerAdapter
import com.indramahkota.footballmatchschedule.ui.match.fragment.NextMatchesFragment
import com.indramahkota.footballmatchschedule.ui.match.fragment.PrevMatchesFragment
import com.indramahkota.footballmatchschedule.ui.search.SearchActivity
import com.indramahkota.footballmatchschedule.ui.search.SearchActivity.Companion.STRING_DATA
import com.indramahkota.footballmatchschedule.viewmodel.LeagueDetailsViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_details.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import javax.inject.Inject

class MatchActivity : AppCompatActivity() {

    companion object {
        const val PARCELABLE_LEAGUE_DATA = "parcelable_league_data"
    }

    private lateinit var league: LeagueModel
    private lateinit var viewModel: LeagueDetailsViewModel

    private var allTeamLoaded = false
    private var nextMatchesLoaded = false
    private var prevMatchesLoaded = false

    private var allTeamData: MutableList<TeamDetailsApiModel> = mutableListOf()
    private var nextMatchesData: MutableList<MatchDetailsApiModel> = mutableListOf()
    private var prevMatchesData: MutableList<MatchDetailsApiModel> = mutableListOf()

    private var newNextMatchesData: MutableList<MatchModel> = mutableListOf()
    private var newPrevMatchesData: MutableList<MatchModel> = mutableListOf()

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        league = intent.getParcelableExtra(PARCELABLE_LEAGUE_DATA)!!

        setSupportActionBar(toolbar)
        supportActionBar?.title = league.strLeague
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LeagueDetailsViewModel::class.java)
        viewModel.leagueDetails.observe(this, Observer<Resource<LeagueDetailsApiResponse?>>{
            when (it.status) {
                Status.SUCCESS -> {
                    Glide.with(this)
                        .load(it.data?.leagues?.get(0)?.strPoster ?: R.drawable.image_error)
                        .error(R.drawable.image_error)
                        .transform(RoundedCorners(8))
                        .into(image_league)

                    strSportData.text = it.data?.leagues?.get(0)?.strSport ?: ""
                    strCountryData.text = it.data?.leagues?.get(0)?.strCountry ?: ""
                    strWebsiteData.text = it.data?.leagues?.get(0)?.strWebsite ?: ""
                }
                Status.ERROR -> toast(R.string.error_load_data)
                else -> {}
            }
        })

        viewModel.allTeamInLeague.observe(this, Observer<Resource<TeamDetailsApiResponse?>>{
            when (it.status) {
                Status.SUCCESS -> {
                    if(it.data?.teams != null) {
                        allTeamData.addAll(it.data.teams)
                    }
                    allTeamLoaded = true
                    checkData()
                }
                Status.ERROR -> toast(R.string.error_load_data)
                else -> {}
            }
        })

        viewModel.nextMatches.observe(this, Observer<Resource<MatchDetailsApiResponse?>>{
            when (it.status) {
                Status.SUCCESS -> {
                    if(it.data?.events != null) {
                        nextMatchesData.addAll(it.data.events)
                    }
                    nextMatchesLoaded = true
                    checkData()
                }
                Status.ERROR -> toast(R.string.error_load_data)
                else -> {}
            }
        })

        viewModel.prevMatches.observe(this, Observer<Resource<MatchDetailsApiResponse?>>{
            when (it.status) {
                Status.SUCCESS -> {
                    if(it.data?.events != null) {
                        prevMatchesData.addAll(it.data.events)
                    }
                    prevMatchesLoaded = true
                    checkData()
                }
                Status.ERROR -> toast(R.string.error_load_data)
                else -> {}
            }
        })

        viewModel.loadAllDetails(league.idLeague)
    }

    private fun checkData() {
        if(allTeamLoaded && nextMatchesLoaded && prevMatchesLoaded)
            setupFragment()
    }

    private fun setupFragment() {
        val listTitle = arrayOf(
            resources.getString(R.string.prev_matches),
            resources.getString(R.string.next_matches)
        )

        for (i in nextMatchesData.indices) {
            val srcImgHomeTeam: String
            val srcImgAwayTeam: String

            val listHelper = allTeamData.filter {
                it.idTeam.equals(nextMatchesData[i].idHomeTeam) ||
                        it.idTeam.equals(nextMatchesData[i].idAwayTeam)
            }

            if(listHelper[0].idTeam.equals(nextMatchesData[i].idHomeTeam)){
                srcImgHomeTeam = listHelper[0].strTeamBadge.toString()
                srcImgAwayTeam = listHelper[1].strTeamBadge.toString()
            } else {
                srcImgHomeTeam = listHelper[1].strTeamBadge.toString()
                srcImgAwayTeam = listHelper[0].strTeamBadge.toString()
            }

            newNextMatchesData.add(MatchModel(nextMatchesData[i].dateEvent ?: "",
                nextMatchesData[i].strHomeTeam ?: "", nextMatchesData[i].strAwayTeam ?: "",
                nextMatchesData[i].intHomeScore ?: "", nextMatchesData[i].intAwayScore ?: "",
                srcImgHomeTeam, srcImgAwayTeam))
        }

        for (i in prevMatchesData.indices) {
            val srcImgHomeTeam: String
            val srcImgAwayTeam: String

            val listHelper = allTeamData.filter {
                it.idTeam.equals(prevMatchesData[i].idHomeTeam) ||
                        it.idTeam.equals(prevMatchesData[i].idAwayTeam)
            }

            if(listHelper[0].idTeam.equals(prevMatchesData[i].idHomeTeam)){
                srcImgHomeTeam = listHelper[0].strTeamBadge.toString()
                srcImgAwayTeam = listHelper[1].strTeamBadge.toString()
            } else {
                srcImgHomeTeam = listHelper[1].strTeamBadge.toString()
                srcImgAwayTeam = listHelper[0].strTeamBadge.toString()
            }

            newPrevMatchesData.add(MatchModel(prevMatchesData[i].dateEvent ?: "",
                prevMatchesData[i].strHomeTeam ?: "", prevMatchesData[i].strAwayTeam ?: "",
                prevMatchesData[i].intHomeScore ?: "", prevMatchesData[i].intAwayScore ?: "",
                srcImgHomeTeam, srcImgAwayTeam))
        }

        Log.d("TESTING", nextMatchesData.size.toString())
        Log.d("TESTING", newNextMatchesData.size.toString())

        Log.d("TESTING", prevMatchesData.size.toString())
        Log.d("TESTING", newPrevMatchesData.size.toString())

        val listFragment = mutableListOf(
            PrevMatchesFragment.newInstance(listTitle[0]),
            NextMatchesFragment.newInstance(listTitle[1])
        )

        val tabPagerAdapter = TabPagerAdapter(supportFragmentManager, listFragment, listTitle)
        tabPagerAdapter.notifyDataSetChanged()
        viewPager.adapter = tabPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabLayout)
        tabs.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search_menu)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                startActivity(intentFor<SearchActivity>(STRING_DATA to query))
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}