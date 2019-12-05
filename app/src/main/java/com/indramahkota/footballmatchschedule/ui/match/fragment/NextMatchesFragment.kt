package com.indramahkota.footballmatchschedule.ui.match.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.Status
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.LeagueApiModel
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.MatchDetailsApiResponse
import com.indramahkota.footballmatchschedule.ui.detail.MatchDetailsActivity
import com.indramahkota.footballmatchschedule.ui.detail.MatchDetailsActivity.Companion.PARCELABLE_MATCH_DATA
import com.indramahkota.footballmatchschedule.ui.match.MatchActivity.Companion.PARCELABLE_LEAGUE_DATA
import com.indramahkota.footballmatchschedule.ui.match.adapter.MatchAdapter
import com.indramahkota.footballmatchschedule.viewmodel.LeagueDetailsViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.content_match_tab.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

class NextMatchesFragment : Fragment() {
    companion object {
        private const val ARG_SECTION_TITLE = "section_title"

        @JvmStatic
        fun newInstance(title: String): NextMatchesFragment {
            return NextMatchesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SECTION_TITLE, title)
                }
            }
        }
    }

    private lateinit var viewModel: LeagueDetailsViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var matchAdapter: MatchAdapter

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.content_match_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearLayoutManager = LinearLayoutManager(view.context)
        rv_category.layoutManager = linearLayoutManager
        rv_category.setHasFixedSize(true)

        matchAdapter = MatchAdapter(null){}
        rv_category.adapter = matchAdapter

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LeagueDetailsViewModel::class.java)
        viewModel.nextMatches.observe(this, Observer<Resource<MatchDetailsApiResponse?>>{
            when (it.status) {
                Status.SUCCESS -> {
                    if(it.data?.events != null) {
                        matchAdapter = MatchAdapter(it.data.events){ matchDetailsApiModel ->
                            startActivity(intentFor<MatchDetailsActivity>(PARCELABLE_MATCH_DATA to matchDetailsApiModel))
                        }
                        rv_category.adapter = matchAdapter
                    } else {
                        no_data.visibility = View.VISIBLE
                    }
                    shimmer_view_container.visibility = View.GONE
                }
                Status.LOADING -> shimmer_view_container.visibility = View.VISIBLE
                Status.ERROR -> toast(R.string.error_load_data)
            }
        })

        val league: LeagueApiModel = activity?.intent?.getParcelableExtra(PARCELABLE_LEAGUE_DATA)!!
        viewModel.loadAllDetails(league.idLeague)
    }
}