package com.indramahkota.footballapp.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.tabs.TabLayout
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.data.source.Resource
import com.indramahkota.footballapp.data.source.Status.ERROR
import com.indramahkota.footballapp.data.source.Status.SUCCESS
import com.indramahkota.footballapp.data.source.locale.entity.LeagueEntity
import com.indramahkota.footballapp.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballapp.ui.activity.SearchActivity.Companion.MATCH_PARCELABLE_DATA
import com.indramahkota.footballapp.ui.activity.SearchActivity.Companion.TEAM_PARCELABLE_DATA
import com.indramahkota.footballapp.ui.fragment.MatchFragment
import com.indramahkota.footballapp.ui.fragment.ClassementFragment
import com.indramahkota.footballapp.ui.fragment.TeamFragment
import com.indramahkota.footballapp.ui.pager.TabPagerAdapter
import com.indramahkota.footballapp.viewmodel.LeagueDetailsViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_league_details.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import javax.inject.Inject

class MatchActivity : AppCompatActivity() {

    companion object {
        const val PARCELABLE_LEAGUE_DATA = "parcelable_league_data"
    }

    private var league: LeagueEntity? = null
    private lateinit var viewModel: LeagueDetailsViewModel

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league_details)

        league = intent?.getParcelableExtra(PARCELABLE_LEAGUE_DATA)

        setSupportActionBar(toolbar)
        supportActionBar?.title = league?.strLeague
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listTitle = arrayOf(
            resources.getString(R.string.match),
            resources.getString(R.string.standings),
            resources.getString(R.string.team)
        )

        val listFragment = mutableListOf(
            MatchFragment.newInstance(resources.getString(R.string.prev_match_fragment)),
            ClassementFragment.newInstance(resources.getString(R.string.next_match_fragment)),
            TeamFragment.newInstance(resources.getString(R.string.team_fragment))
        )

        val tabPagerAdapter =
            TabPagerAdapter(
                supportFragmentManager,
                listFragment,
                listTitle
            )
        tabPagerAdapter.notifyDataSetChanged()
        viewPager.adapter = tabPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabLayout)
        tabs.setupWithViewPager(viewPager)

        viewModel = ViewModelProvider(this, viewModelFactory).get(LeagueDetailsViewModel::class.java)
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

        league?.idLeague?.let { viewModel.loadAllDetails(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search_icon, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.search_menu_icon -> {
                startActivity(intentFor<SearchActivity>(
                    MATCH_PARCELABLE_DATA to viewModel.getAllMatchsData(),
                    TEAM_PARCELABLE_DATA to viewModel.getAllTeamData()
                ))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}