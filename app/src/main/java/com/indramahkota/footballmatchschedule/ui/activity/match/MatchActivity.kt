package com.indramahkota.footballmatchschedule.ui.activity.match

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
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
import com.indramahkota.footballmatchschedule.data.source.Status.*
import com.indramahkota.footballmatchschedule.data.source.locale.entity.LeagueEntity
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballmatchschedule.ui.activity.search.SearchActivity
import com.indramahkota.footballmatchschedule.ui.activity.search.SearchActivity.Companion.PARCELABLE_DATA
import com.indramahkota.footballmatchschedule.ui.activity.search.SearchActivity.Companion.STRING_DATA
import com.indramahkota.footballmatchschedule.ui.fragment.adapter.TabPagerAdapter
import com.indramahkota.footballmatchschedule.ui.fragment.match.MatchFragment
import com.indramahkota.footballmatchschedule.viewmodel.LeagueDetailsViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_league_details.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject

class MatchActivity : AppCompatActivity() {

    companion object {
        const val PARCELABLE_LEAGUE_DATA = "parcelable_league_data"
    }

    private lateinit var league: LeagueEntity
    private lateinit var viewModel: LeagueDetailsViewModel

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league_details)

        league = intent.getParcelableExtra(PARCELABLE_LEAGUE_DATA)!!

        setSupportActionBar(toolbar)
        supportActionBar?.title = league.strLeague
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listTitle = arrayOf(
            resources.getString(R.string.prev_matches),
            resources.getString(R.string.next_matches)
        )

        val listFragment = mutableListOf(
            MatchFragment.newInstance(resources.getString(R.string.prev_matches_fragment)),
            MatchFragment.newInstance(resources.getString(R.string.next_matches_fragment))
        )

        val tabPagerAdapter = TabPagerAdapter(supportFragmentManager, listFragment, listTitle)
        tabPagerAdapter.notifyDataSetChanged()
        viewPager.adapter = tabPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabLayout)
        tabs.setupWithViewPager(viewPager)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LeagueDetailsViewModel::class.java)
        viewModel.leagueDetails.observe(this, Observer<Resource<LeagueDetailsApiResponse?>>{
            when (it.status) {
                SUCCESS -> {
                    Glide.with(this)
                        .load(it.data?.leagues?.get(0)?.strPoster ?: R.drawable.image_error)
                        .placeholder(R.drawable.spinner_animation)
                        .error(R.drawable.image_error)
                        .transform(RoundedCorners(8))
                        .into(image_league)

                    strSportData.text = it.data?.leagues?.get(0)?.strSport ?: ""
                    strCountryData.text = it.data?.leagues?.get(0)?.strCountry ?: ""
                    strWebsiteData.text = it.data?.leagues?.get(0)?.strWebsite ?: ""
                }
                ERROR -> toast(it.message.toString())
            }
        })
        viewModel.loadAllDetails(league.idLeague)
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
                startActivity(intentFor<SearchActivity>(
                    STRING_DATA to query.toLowerCase(Locale.getDefault()),
                    PARCELABLE_DATA to viewModel.getAllMatchsData()))
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