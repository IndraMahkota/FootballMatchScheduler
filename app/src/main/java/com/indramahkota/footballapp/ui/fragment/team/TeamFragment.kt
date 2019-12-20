package com.indramahkota.footballapp.ui.fragment.team

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.data.source.Resource
import com.indramahkota.footballapp.data.source.Status.ERROR
import com.indramahkota.footballapp.data.source.Status.SUCCESS
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity
import com.indramahkota.footballapp.ui.adapter.team.TeamAdapter
import com.indramahkota.footballapp.viewmodel.LeagueDetailsViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_team.*
import org.jetbrains.anko.support.v4.toast
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

        rv_all_team.setHasFixedSize(true)

        val listTeamData = mutableListOf<TeamEntity>()
        allTeamAdapter = TeamAdapter(listTeamData) {}
        rv_all_team.adapter = allTeamAdapter

        if(allTeamData != null) {
            Log.d("Test", "Test1")
            initialize(allTeamData)
        } else {
            Log.d("Test", "Test2")
            getAllTeamData()
        }

        Log.d("Test", "Test3")
    }

    private fun getAllTeamData() {
        val viewModel = activity?.let { ViewModelProvider(it, viewModelFactory).get(
            LeagueDetailsViewModel::class.java) }
        viewModel?.allTeamData?.observe(viewLifecycleOwner, Observer<Resource<List<TeamEntity>?>> {
            checkState(it)
        })
    }

    private fun checkState(it: Resource<List<TeamEntity>?>){
        when (it.status) {
            SUCCESS -> {
                initialize(it.data)
            }
            ERROR -> toast(it.message.toString())
        }
    }

    private fun initialize(it: List<TeamEntity>?) {
        if(it.isNullOrEmpty()) {

            Log.d("Test", "Test4")

            no_data.visibility = View.VISIBLE
        } else {
            Log.d("Test", "Test6")
            no_data.visibility = View.INVISIBLE
        }

        Log.d("Test", "Test5")

        allTeamData = it?.let { ArrayList(it) }
        it?.let { allTeamAdapter.replace(it) }

        shimmer_view_container.visibility = View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(ARG_SAVE_DATA, allTeamData)
    }
}
