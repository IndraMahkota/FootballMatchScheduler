package com.indramahkota.footballmatchschedule.ui.activity.match

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.tabs.TabLayout
import com.indramahkota.footballmatchschedule.EspressoIdlingResource
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.Status.ERROR
import com.indramahkota.footballmatchschedule.data.source.Status.SUCCESS
import com.indramahkota.footballmatchschedule.data.source.locale.entity.LeagueEntity
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.LeagueDetailsApiResponse
import com.indramahkota.footballmatchschedule.ui.activity.search.SearchActivity
import com.indramahkota.footballmatchschedule.ui.activity.search.SearchActivity.Companion.PARCELABLE_DATA
import com.indramahkota.footballmatchschedule.ui.fragment.match.MatchFragment
import com.indramahkota.footballmatchschedule.ui.fragment.standing.StandingFragment
import com.indramahkota.footballmatchschedule.ui.fragment.team.TeamFragment
import com.indramahkota.footballmatchschedule.ui.pager.tab.TabPagerAdapter
import com.indramahkota.footballmatchschedule.viewmodel.LeagueDetailsViewModel
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
            StandingFragment.newInstance(resources.getString(R.string.next_match_fragment)),
            TeamFragment.newInstance(resources.getString(R.string.next_match_fragment))
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

        EspressoIdlingResource.increment()

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

                    EspressoIdlingResource.decrement()
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
                    PARCELABLE_DATA to viewModel.getAllMatchsData()))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}