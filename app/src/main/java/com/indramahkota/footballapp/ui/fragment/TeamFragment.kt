package com.indramahkota.footballapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity
import com.indramahkota.footballapp.data.source.repository.Result
import com.indramahkota.footballapp.ui.activity.DetailsTeamActivity
import com.indramahkota.footballapp.ui.activity.DetailsTeamActivity.Companion.PARCELABLE_TEAM_DATA
import com.indramahkota.footballapp.ui.adapter.TeamAdapter
import com.indramahkota.footballapp.viewmodel.FavoriteViewModel
import com.indramahkota.footballapp.viewmodel.LeagueDetailsViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_team.*
import javax.inject.Inject

class TeamFragment : Fragment() {
    companion object {
        private const val ARG_SECTION_FRAGMENT = "section_fragment"
        private const val ARG_SAVE_DATA = "save_data"

        @JvmStatic
        fun newInstance(fragment: String) = TeamFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_SECTION_FRAGMENT, fragment)
            }
        }
    }

    private var state: String? = null
    private var allTeamData: ArrayList<TeamEntity>? = null

    private lateinit var allTeamAdapter: TeamAdapter

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        allTeamData = savedInstanceState?.getParcelableArrayList(ARG_SAVE_DATA)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        state = arguments?.getString(ARG_SECTION_FRAGMENT)

        rv_all_team.setHasFixedSize(true)

        val listTeamData = mutableListOf<TeamEntity>()
        allTeamAdapter =
            TeamAdapter(listTeamData) { teamEntity ->
                run {
                    val intent = Intent(activity, DetailsTeamActivity::class.java).apply {
                        putExtra(PARCELABLE_TEAM_DATA, teamEntity)
                    }
                    startActivity(intent)
                }
            }
        rv_all_team.adapter = allTeamAdapter

        if (allTeamData != null) {
            initialize(allTeamData)
        } else {
            when (state) {
                resources.getString(R.string.team_fragment) -> getAllTeamData()
                resources.getString(R.string.favorite_team_fragment) -> getAllFavoriteTeamData()
            }
        }
    }

    private fun getAllTeamData() {
        val viewModel = activity?.let {
            ViewModelProvider(it, viewModelFactory).get(
                LeagueDetailsViewModel::class.java
            )
        }
        viewModel?.allTeamData?.observe(viewLifecycleOwner, Observer<Result<List<TeamEntity>?>> {
            checkState(it)
        })
    }

    private fun getAllFavoriteTeamData() {
        val viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)
        viewModel.getAllFavoriteTeam().observe(viewLifecycleOwner, Observer<List<TeamEntity>> {
            initialize(it)
        })
    }

    private fun checkState(it: Result<List<TeamEntity>?>) {
        when (it) {
            is Result.Success -> {
                initialize(it.data)
            }
            is Result.Error -> Toast.makeText(activity, it.exception.toString(), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun initialize(it: List<TeamEntity>?) {
        if (it.isNullOrEmpty()) {
            no_data.visibility = View.VISIBLE
        } else {
            no_data.visibility = View.INVISIBLE
        }

        allTeamData = it?.let { ArrayList(it) }
        it?.let { allTeamAdapter.replace(it) }

        shimmer_view_container.visibility = View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(ARG_SAVE_DATA, allTeamData)
    }

    override fun onResume() {
        super.onResume()

        when (state) {
            resources.getString(R.string.favorite_team_fragment) -> getAllFavoriteTeamData()
        }
    }
}
