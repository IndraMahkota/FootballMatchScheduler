package com.indramahkota.footballmatchschedule.ui.activity.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.snackbar.Snackbar
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.Status
import com.indramahkota.footballmatchschedule.data.source.locale.entity.MatchEntity
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.MatchDetailsApiModel
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.TeamDetailsApiModel
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.TeamDetailsApiResponse
import com.indramahkota.footballmatchschedule.utilities.Utilities.formatDateFromString
import com.indramahkota.footballmatchschedule.viewmodel.FavoriteMatchViewModel
import com.indramahkota.footballmatchschedule.viewmodel.MatchDetailsViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_matchs_details.*
import kotlinx.android.synthetic.main.layout_text_views.view.*
import org.jetbrains.anko.toast
import javax.inject.Inject


class MatchDetailsActivity : AppCompatActivity() {

    companion object {
        const val PARCELABLE_MATCH_DATA = "parcelable_match_data"
    }

    private var favMatch: MatchEntity? = null
    private var matchDetailsData: MatchDetailsApiModel? = null
    private var homeTeamDetailsData: TeamDetailsApiModel? = null
    private var awayTeamDetailsData: TeamDetailsApiModel? = null

    private lateinit var matchDetail: MatchEntity
    private lateinit var viewModel: MatchDetailsViewModel
    private lateinit var favViewModel: FavoriteMatchViewModel

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matchs_details)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        matchDetail = intent.getParcelableExtra(PARCELABLE_MATCH_DATA)!!

        viewModel = ViewModelProvider(this, viewModelFactory).get(MatchDetailsViewModel::class.java)
        viewModel.matchDetails.observe(this, Observer<Resource<MatchDetailsApiResponse?>>{
            when (it.status) {
                Status.SUCCESS -> {
                    if(it.data?.events != null) {
                        initializeUi(it.data.events[0])

                        matchDetailsData = it.data.events[0]
                        updateFavorite(matchDetailsData, homeTeamDetailsData, awayTeamDetailsData)
                    }
                }
                Status.ERROR -> toast(it.message.toString())
            }
        })

        viewModel.homeTeamDetails.observe(this, Observer<Resource<TeamDetailsApiResponse?>>{
            when (it.status) {
                Status.SUCCESS -> {
                    if(it.data?.teams != null) {
                        Glide.with(this)
                            .load(it.data.teams[0].strTeamBadge ?: R.drawable.image_error)
                            .placeholder(R.drawable.spinner_animation)
                            .error(R.drawable.image_error)
                            .transform(RoundedCorners(8))
                            .into(ivTeam1Logo)

                        homeTeamDetailsData = it.data.teams[0]
                        updateFavorite(matchDetailsData, homeTeamDetailsData, awayTeamDetailsData)
                    }
                }
                Status.ERROR -> toast(it.message.toString())
            }
        })

        viewModel.awayTeamDetails.observe(this, Observer<Resource<TeamDetailsApiResponse?>>{
            when (it.status) {
                Status.SUCCESS -> {
                    if(it.data?.teams != null) {
                        Glide.with(this)
                            .load(it.data.teams[0].strTeamBadge ?: R.drawable.image_error)
                            .placeholder(R.drawable.spinner_animation)
                            .error(R.drawable.image_error)
                            .transform(RoundedCorners(8))
                            .into(ivTeam2Logo)

                        awayTeamDetailsData = it.data.teams[0]
                        updateFavorite(matchDetailsData, homeTeamDetailsData, awayTeamDetailsData)
                    }
                }
                Status.ERROR -> toast(it.message.toString())
            }
        })

        favViewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteMatchViewModel::class.java)
        favViewModel.favoriteById.observe(this, Observer<MatchEntity>{
            if(it != null){
                favMatch = it
                fab.setImageResource(R.drawable.ic_star_pink)
            }
        })

        matchDetail.idEvent.let { favViewModel.getFavoriteById(it) }
        matchDetail.idEvent.let { viewModel.loadMatchDetails(it) }
        matchDetail.idHomeTeam.let { viewModel.loadHomeTeamDetails(it) }
        matchDetail.idAwayTeam.let { viewModel.loadAwayTeamDetails(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun updateFavorite(match: MatchDetailsApiModel?,
                               homeTeam: TeamDetailsApiModel?,
                               awayTeam: TeamDetailsApiModel?){
        if(favMatch != null){
            val newData = createNewFavoriteData(match, homeTeam, awayTeam)
            favViewModel.updateFavorite(newData)
        }
    }

    private fun createNewFavoriteData(match: MatchDetailsApiModel?,
                                      homeTeam: TeamDetailsApiModel?,
                                      awayTeam: TeamDetailsApiModel?):MatchEntity {
        return MatchEntity(
            match?.idEvent ?: "",
            match?.idHomeTeam ?: "",
            match?.idAwayTeam ?: "",
            match?.dateEvent ?: "",
            match?.strHomeTeam ?: "",
            match?.strAwayTeam ?: "",
            match?.intHomeScore ?: "-",
            match?.intAwayScore ?: "-",
            homeTeam?.strTeamBadge ?: "",
            awayTeam?.strTeamBadge ?: ""
        )
    }

    private fun initializeUi(data: MatchDetailsApiModel) {
        fab.setOnClickListener { view ->
            run {
                val message: String
                if(favMatch != null){
                    favViewModel.deleteFavorite(favMatch!!)
                    favMatch = null
                    fab.setImageResource(R.drawable.ic_star_white_border)
                    message = "Delete favorite"
                } else{
                    val newData = createNewFavoriteData(data, homeTeamDetailsData, awayTeamDetailsData)
                    favViewModel.insertFavorite(newData)
                    favMatch = newData
                    fab.setImageResource(R.drawable.ic_star_pink)
                    message = "Insert to favorite"
                }

                Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }

        tvDate.text = formatDateFromString(data.dateEvent ?: "")
        tvSkorTeam1.text = data.intHomeScore ?: "-"
        tvSkorTeam2.text = data.intAwayScore ?: "-"
        tvTeam1Name.text = data.strHomeTeam ?: "-"
        tvTeam2Name.text = data.strAwayTeam ?: "-"

        formation.tvLeft.text = data.strHomeFormation ?: "-"
        formation.tvCenter.text = getString(R.string.formation)
        formation.tvRight.text = data.strAwayFormation ?: "-"

        goal_details.tvLeft.text = data.strHomeGoalDetails ?: "-"
        goal_details.tvCenter.text = getString(R.string.goal)
        goal_details.tvRight.text = data.strAwayGoalDetails ?: "-"

        lineup_goal_keeper.tvLeft.text = data.strHomeLineupGoalkeeper ?: "-"
        lineup_goal_keeper.tvCenter.text = getString(R.string.lineup_goal_keeper)
        lineup_goal_keeper.tvRight.text = data.strAwayLineupGoalkeeper ?: "-"

        lineup_defense.tvLeft.text = data.strHomeLineupDefense ?: "-"
        lineup_defense.tvCenter.text = getString(R.string.lineup_defense)
        lineup_defense.tvRight.text = data.strAwayLineupDefense ?: "-"

        lineup_mid_field.tvLeft.text = data.strHomeLineupMidfield ?: "-"
        lineup_mid_field.tvCenter.text = getString(R.string.lineup_mid_field)
        lineup_mid_field.tvRight.text = data.strAwayLineupMidfield ?: "-"

        lineup_forward.tvLeft.text = data.strHomeLineupForward ?: "-"
        lineup_forward.tvCenter.text = getString(R.string.lineup_forward)
        lineup_forward.tvRight.text = data.strAwayLineupForward ?: "-"

        lineup_substitutes.tvLeft.text = data.strHomeLineupSubstitutes ?: "-"
        lineup_substitutes.tvCenter.text = getString(R.string.lineup_subtitutes)
        lineup_substitutes.tvRight.text = data.strAwayLineupSubstitutes ?: "-"

        red_cards.tvLeft.text = data.strHomeRedCards ?: "-"
        red_cards.tvCenter.text = getString(R.string.red_cards)
        red_cards.tvRight.text = data.strAwayRedCards ?: "-"

        yellow_cards.tvLeft.text = data.strHomeYellowCards ?: "-"
        yellow_cards.tvCenter.text = getString(R.string.yellow_cards)
        yellow_cards.tvRight.text = data.strAwayYellowCards ?: "-"

        shimmer_view_container.visibility = View.GONE
    }
}
