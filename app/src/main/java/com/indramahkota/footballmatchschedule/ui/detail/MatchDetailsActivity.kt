package com.indramahkota.footballmatchschedule.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.snackbar.Snackbar
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.Status
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.MatchDetailsApiModel
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.TeamDetailsApiResponse
import com.indramahkota.footballmatchschedule.utilities.formatDateFromString
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

    private lateinit var matchDetail: MatchDetailsApiModel
    private lateinit var viewModel: MatchDetailsViewModel

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matchs_details)

        matchDetail = intent.getParcelableExtra(PARCELABLE_MATCH_DATA)!!

        Glide.with(this)
            .load(R.drawable.spinner_animation)
            .into(ivTeam1Logo)

        Glide.with(this)
            .load(R.drawable.spinner_animation)
            .into(ivTeam2Logo)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MatchDetailsViewModel::class.java)
        viewModel.matchDetails.observe(this, Observer<Resource<MatchDetailsApiResponse?>>{
            when (it.status) {
                Status.SUCCESS -> {
                    if(it.data?.events != null) {
                        initializeUi(it.data.events[0])
                    }
                }
                Status.ERROR -> toast(R.string.error_load_data)
                else -> {}
            }
        })
        matchDetail.idEvent?.let { viewModel.loadMatchDetails(it) }

        viewModel.homeTeamDetails.observe(this, Observer<Resource<TeamDetailsApiResponse?>>{
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("TESTING_FAIL", "home scs")
                    if(it.data?.teams != null) {
                        Glide.with(this)
                            .load(it.data.teams[0].strTeamBadge ?: R.drawable.image_error)
                            .placeholder(R.drawable.spinner_animation)
                            .error(R.drawable.image_error)
                            .transform(RoundedCorners(8))
                            .into(ivTeam1Logo)
                    }
                }
                Status.ERROR -> toast(R.string.error_load_data)
                else -> {}
            }
            Log.d("TESTING_FAIL", "home")
        })

        viewModel.awayTeamDetails.observe(this, Observer<Resource<TeamDetailsApiResponse?>>{
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("TESTING_FAIL", "away scs")
                    if(it.data?.teams != null) {
                        Glide.with(this)
                            .load(it.data.teams[0].strTeamBadge ?: R.drawable.image_error)
                            .placeholder(R.drawable.spinner_animation)
                            .error(R.drawable.image_error)
                            .transform(RoundedCorners(8))
                            .into(ivTeam2Logo)
                    }
                }
                Status.ERROR -> toast(R.string.error_load_data)
                else -> {}
            }
            Log.d("TESTING_FAIL", "away")
        })
    }

    private fun initializeUi(data: MatchDetailsApiModel) {
        data.idHomeTeam?.let { viewModel.loadHomeTeamDetails(it) }
        data.idAwayTeam?.let { viewModel.loadAwayTeamDetails(it) }

        Log.d("TESTING_FAIL", "shit")

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
    }
}
