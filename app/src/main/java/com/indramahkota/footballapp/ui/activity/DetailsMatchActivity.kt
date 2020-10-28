package com.indramahkota.footballapp.ui.activity

import android.graphics.Matrix
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.snackbar.Snackbar
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.data.source.remote.model.MatchDetailsModel
import com.indramahkota.footballapp.data.source.remote.model.TeamDetailsiModel
import com.indramahkota.footballapp.data.source.repository.Result
import com.indramahkota.footballapp.utilities.Utilities.formatDateFromString
import com.indramahkota.footballapp.viewmodel.FavoriteViewModel
import com.indramahkota.footballapp.viewmodel.MatchDetailsViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_match_details.*
import kotlinx.android.synthetic.main.layout_text_views.view.*
import javax.inject.Inject

class DetailsMatchActivity : DaggerAppCompatActivity() {

    companion object {
        const val PARCELABLE_MATCH_DATA = "parcelable_match_data"
    }

    private var favoriteMatch: MatchEntity? = null
    private var matchEntity: MatchEntity? = null
    private var matchDetailsData: MatchDetailsModel? = null
    private var homeTeamDetailsData: TeamDetailsiModel? = null
    private var awayTeamDetailsData: TeamDetailsiModel? = null

    private lateinit var viewModel: MatchDetailsViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_details)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        matchEntity = intent?.getParcelableExtra(PARCELABLE_MATCH_DATA)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MatchDetailsViewModel::class.java)
        viewModel.matchDetails.observe(this, {
            when (it) {
                is Result.Success -> {
                    if (it.data?.events != null) {
                        initializeUi(it.data.events[0])
                        matchDetailsData = it.data.events[0]
                        updateFavorite(matchDetailsData, homeTeamDetailsData, awayTeamDetailsData)
                    }
                }
                is Result.Error -> Toast.makeText(
                    applicationContext,
                    it.exception.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        viewModel.homeTeamDetails.observe(this, {
            when (it) {
                is Result.Success -> {
                    if (it.data?.teams != null) {
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
                is Result.Error -> Toast.makeText(
                    applicationContext,
                    it.exception.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        viewModel.awayTeamDetails.observe(this, {
            when (it) {
                is Result.Success -> {
                    if (it.data?.teams != null) {
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
                is Result.Error -> Toast.makeText(
                    applicationContext,
                    it.exception.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        favoriteViewModel =
            ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)
        favoriteViewModel.favoriteMatchById.observe(this, {
            if (it != null) {
                favoriteMatch = it
                fab.setImageResource(R.drawable.ic_star_pink)
            }
        })

        matchEntity?.idEvent?.let { favoriteViewModel.getFavoriteMatchById(it) }
        matchEntity?.idEvent?.let { viewModel.loadMatchDetails(it) }
        matchEntity?.idHomeTeam?.let { viewModel.loadHomeTeamDetails(it) }
        matchEntity?.idAwayTeam?.let { viewModel.loadAwayTeamDetails(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun updateFavorite(
        match: MatchDetailsModel?,
        homeTeam: TeamDetailsiModel?,
        awayTeam: TeamDetailsiModel?
    ) {
        if (favoriteMatch != null) {
            val newData = createNewFavoriteData(match, homeTeam, awayTeam)
            favoriteViewModel.updateFavoriteMatch(newData)
        }
    }

    private fun createNewFavoriteData(
        match: MatchDetailsModel?,
        homeTeam: TeamDetailsiModel?,
        awayTeam: TeamDetailsiModel?
    ): MatchEntity {
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

    private fun initializeUi(data: MatchDetailsModel) {
        fab.setOnClickListener { view ->
            val message: String
            val favorite = favoriteMatch
            if (favorite != null) {
                favoriteViewModel.deleteFavoriteMatch(favorite)
                favoriteMatch = null
                fab.setImageResource(R.drawable.ic_star_white_border)
                fab.imageMatrix = Matrix()
                message = resources.getString(R.string.delete_favorite)
            } else {
                val newData = createNewFavoriteData(data, homeTeamDetailsData, awayTeamDetailsData)
                favoriteViewModel.insertFavoriteMatch(newData)
                favoriteMatch = newData
                fab.setImageResource(R.drawable.ic_star_pink)
                fab.imageMatrix = Matrix()
                message = resources.getString(R.string.insert_favorite)
            }

            Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction(resources.getString(R.string.action), null).show()
        }

        tvDate.text = formatDateFromString(data.dateEvent ?: "")
        tvSkorTeam1.text = data.intHomeScore ?: "-"
        tvSkorTeam2.text = data.intAwayScore ?: "-"
        tvTeam1Name.text = data.strHomeTeam ?: "-"
        tvTeam2Name.text = data.strAwayTeam ?: "-"

        if (checkNullAndEmpty(data.strHomeFormation, data.strAwayFormation)) {
            formation.tvLeft.text = data.strHomeFormation
            formation.tvCenter.text = getString(R.string.formation)
            formation.tvRight.text = data.strAwayFormation
        }

        if (checkNullAndEmpty(data.strHomeGoalDetails, data.strAwayGoalDetails)) {
            goal_details.tvLeft.text = data.strHomeGoalDetails
            goal_details.tvCenter.text = getString(R.string.goal)
            goal_details.tvRight.text = data.strAwayGoalDetails
        }

        if (checkNullAndEmpty(data.strHomeLineupGoalkeeper, data.strAwayLineupGoalkeeper)) {
            lineup_goal_keeper.tvLeft.text = data.strHomeLineupGoalkeeper
            lineup_goal_keeper.tvCenter.text = getString(R.string.lineup_goal_keeper)
            lineup_goal_keeper.tvRight.text = data.strAwayLineupGoalkeeper
        }

        if (checkNullAndEmpty(data.strHomeLineupDefense, data.strAwayLineupDefense)) {
            lineup_defense.tvLeft.text = data.strHomeLineupDefense
            lineup_defense.tvCenter.text = getString(R.string.lineup_defense)
            lineup_defense.tvRight.text = data.strAwayLineupDefense
        }

        if (checkNullAndEmpty(data.strHomeLineupMidfield, data.strAwayLineupMidfield)) {
            lineup_mid_field.tvLeft.text = data.strHomeLineupMidfield
            lineup_mid_field.tvCenter.text = getString(R.string.lineup_mid_field)
            lineup_mid_field.tvRight.text = data.strAwayLineupMidfield
        }

        if (checkNullAndEmpty(data.strHomeLineupForward, data.strAwayLineupForward)) {
            lineup_forward.tvLeft.text = data.strHomeLineupForward
            lineup_forward.tvCenter.text = getString(R.string.lineup_forward)
            lineup_forward.tvRight.text = data.strAwayLineupForward
        }

        if (checkNullAndEmpty(data.strHomeLineupSubstitutes, data.strAwayLineupSubstitutes)) {
            lineup_substitutes.tvLeft.text = data.strHomeLineupSubstitutes
            lineup_substitutes.tvCenter.text = getString(R.string.lineup_subtitutes)
            lineup_substitutes.tvRight.text = data.strAwayLineupSubstitutes
        }

        if (checkNullAndEmpty(data.strHomeRedCards, data.strAwayRedCards)) {
            red_cards.tvLeft.text = data.strHomeRedCards
            red_cards.tvCenter.text = getString(R.string.red_cards)
            red_cards.tvRight.text = data.strAwayRedCards
        }

        if (checkNullAndEmpty(data.strHomeYellowCards, data.strAwayYellowCards)) {
            yellow_cards.tvLeft.text = data.strHomeYellowCards
            yellow_cards.tvCenter.text = getString(R.string.yellow_cards)
            yellow_cards.tvRight.text = data.strAwayYellowCards
        }

        val list = mutableListOf<String?>()
        list.add(data.strHomeFormation)
        list.add(data.strAwayFormation)
        list.add(data.strHomeGoalDetails)
        list.add(data.strAwayGoalDetails)
        list.add(data.strHomeLineupGoalkeeper)
        list.add(data.strAwayLineupGoalkeeper)
        list.add(data.strHomeLineupDefense)
        list.add(data.strAwayLineupDefense)
        list.add(data.strHomeLineupMidfield)
        list.add(data.strAwayLineupMidfield)
        list.add(data.strHomeLineupForward)
        list.add(data.strAwayLineupForward)
        list.add(data.strHomeLineupSubstitutes)
        list.add(data.strAwayLineupSubstitutes)
        list.add(data.strHomeRedCards)
        list.add(data.strAwayRedCards)
        list.add(data.strHomeYellowCards)
        list.add(data.strAwayYellowCards)

        if (!checkAllNullAndEmpty(list)) {
            no_data.visibility = View.VISIBLE
        }

        shimmer_view_container.visibility = View.GONE
    }

    private fun checkNullAndEmpty(str1: String?, str2: String?): Boolean {
        return (str1 != null && str1.isNotEmpty()) ||
                (str2 != null && str2.isNotEmpty())
    }

    private fun checkAllNullAndEmpty(list: List<String?>): Boolean {
        var result = false
        for (str in list) {
            if (str != null && str.isNotEmpty()) {
                result = true
                break
            }
        }
        return result
    }
}
