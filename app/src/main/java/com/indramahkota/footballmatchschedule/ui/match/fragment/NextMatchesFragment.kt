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
import com.indramahkota.footballmatchschedule.data.source.remote.model.MatchModel
import com.indramahkota.footballmatchschedule.ui.detail.MatchDetailsActivity
import com.indramahkota.footballmatchschedule.ui.detail.MatchDetailsActivity.Companion.PARCELABLE_MATCH_DATA
import com.indramahkota.footballmatchschedule.ui.match.adapter.MatchAdapter
import com.indramahkota.footballmatchschedule.viewmodel.LeagueDetailsViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.content_match_tab.*
import org.jetbrains.anko.support.v4.intentFor
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

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LeagueDetailsViewModel::class.java)
        viewModel.newNextMatchesData.observe(this, Observer<List<MatchModel>>{
            if(it.isNotEmpty()){
                linearLayoutManager = LinearLayoutManager(view.context)
                rv_category.layoutManager = linearLayoutManager
                rv_category.setHasFixedSize(true)

                matchAdapter = MatchAdapter(it){ matchDetailsApiModel ->
                    startActivity(intentFor<MatchDetailsActivity>(PARCELABLE_MATCH_DATA to matchDetailsApiModel))
                }
                rv_category.adapter = matchAdapter
            } else {
                no_data.visibility = View.VISIBLE
            }
            shimmer_view_container.visibility = View.GONE
        })
    }
}