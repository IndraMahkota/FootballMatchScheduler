package com.indramahkota.footballapp.ui.activity

import android.graphics.Matrix
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.snackbar.Snackbar
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.data.source.Resource
import com.indramahkota.footballapp.data.source.Status.ERROR
import com.indramahkota.footballapp.data.source.Status.SUCCESS
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity
import com.indramahkota.footballapp.data.source.remote.model.TeamDetailsiModel
import com.indramahkota.footballapp.data.source.remote.model.TeamDetailsResponse
import com.indramahkota.footballapp.utilities.toTeamEntity
import com.indramahkota.footballapp.viewmodel.FavoriteViewModel
import com.indramahkota.footballapp.viewmodel.TeamDetailsViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_team_details.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class DetailsTeamActivity : AppCompatActivity() {

    companion object {
        const val PARCELABLE_TEAM_DATA = "parcelable_team_data"
    }

    private var favoriteTeam: TeamEntity? = null
    private var teamEntity: TeamEntity? = null
    private var teamDetailsData: TeamDetailsiModel? = null

    private lateinit var viewModel: TeamDetailsViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_details)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        teamEntity = intent?.getParcelableExtra(PARCELABLE_TEAM_DATA)

        viewModel = ViewModelProvider(this, viewModelFactory).get(TeamDetailsViewModel::class.java)
        viewModel.teamDetails.observe(this, Observer<Resource<TeamDetailsResponse?>>{
            when (it.status) {
                SUCCESS -> {
                    if(it.data?.teams != null) {
                        initializeUi(it.data.teams[0])
                        teamDetailsData = it.data.teams[0]
                        updateFavorite(it.data.teams[0])
                    }
                }
                ERROR -> toast(it.message.toString())
            }
        })

        favoriteViewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)
        favoriteViewModel.favoriteTeamById.observe(this, Observer<TeamEntity>{
            if(it != null){
                favoriteTeam = it
                fab.setImageResource(R.drawable.ic_star_pink)
            }
        })

        teamEntity?.idTeam?.let {favoriteViewModel.getFavoriteTeamById(it)}
        teamEntity?.idTeam?.let {viewModel.loadTeamDetails(it)}
    }

    private fun initializeUi(data: TeamDetailsiModel) {
        fab.setOnClickListener { view ->
            val message: String
            val favorite = favoriteTeam
            if(favorite != null){
                favoriteViewModel.deleteFavoriteTeam(favorite)
                favoriteTeam = null
                fab.setImageResource(R.drawable.ic_star_white_border)
                fab.imageMatrix = Matrix()
                message = "Delete favorite"
            } else{
                val newData = data.toTeamEntity()
                favoriteViewModel.insertFavoriteTeam(newData)
                favoriteTeam = newData
                fab.setImageResource(R.drawable.ic_star_pink)
                fab.imageMatrix = Matrix()
                message = "Insert to favorite"
            }

            Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
        }

        Glide.with(this)
            .load(data.strTeamBadge ?: R.drawable.image_error)
            .placeholder(R.drawable.spinner_animation)
            .error(R.drawable.image_error)
            .transform(RoundedCorners(8))
            .into(ivTeamLogo)

        strTeam.text = data.strTeam
        strDescription.text = data.strDescriptionEN
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun updateFavorite(team: TeamDetailsiModel){
        if(favoriteTeam != null){
            val newData = team.toTeamEntity()
            favoriteViewModel.updateFavoriteTeam(newData)
        }
    }
}