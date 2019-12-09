package com.indramahkota.footballmatchschedule.ui.fragment.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.Status.ERROR
import com.indramahkota.footballmatchschedule.data.source.Status.SUCCESS
import com.indramahkota.footballmatchschedule.data.source.locale.entity.MatchEntity
import com.indramahkota.footballmatchschedule.ui.activity.detail.MatchDetailsActivity
import com.indramahkota.footballmatchschedule.ui.activity.detail.MatchDetailsActivity.Companion.PARCELABLE_MATCH_DATA
import com.indramahkota.footballmatchschedule.ui.fragment.match.adapter.MatchAdapter
import com.indramahkota.footballmatchschedule.utilities.Utilities.compareDateAfter
import com.indramahkota.footballmatchschedule.utilities.Utilities.compareDateBeforeAndEqual
import com.indramahkota.footballmatchschedule.viewmodel.FavoriteMatchViewModel
import com.indramahkota.footballmatchschedule.viewmodel.LeagueDetailsViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.content_match_tab.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

class MatchFragment : Fragment() {
    companion object {
        private const val ARG_SECTION_FRAGMENT = "section_fragment"
        private const val ARG_SAVE_DATA = "save_data"

        @JvmStatic
        fun newInstance(fragment: String): MatchFragment {
            return MatchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SECTION_FRAGMENT, fragment)
                }
            }
        }
    }

    private var matchsData: ArrayList<MatchEntity>? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var matchAdapter: MatchAdapter

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        savedInstanceState?.run {
            matchsData?.addAll(savedInstanceState.getParcelableArrayList(ARG_SAVE_DATA)!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_match_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val state = arguments?.getString(ARG_SECTION_FRAGMENT)

        if(matchsData != null){
            initializeUi(view, matchsData)
        } else {
            when (state) {
                resources.getString(R.string.prev_matches_fragment) -> getPrevListData(view)
                resources.getString(R.string.next_matches_fragment) -> getNextListData(view)
                resources.getString(R.string.prev_favorite_matches_fragment) -> getFavoritePrevListData(view)
                resources.getString(R.string.next_favorite_matches_fragment) -> getFavoriteNextListData(view)
            }
        }
    }

    private fun getPrevListData(view: View) {
        val viewModel = activity?.let { ViewModelProvider(it, viewModelFactory).get(LeagueDetailsViewModel::class.java) }
        viewModel?.newPrevMatchesData?.observe(viewLifecycleOwner, Observer<Resource<List<MatchEntity>?>> {
            checkState(view, it)
        })
    }

    private fun getNextListData(view: View) {
        val viewModel = activity?.let { ViewModelProvider(it, viewModelFactory).get(LeagueDetailsViewModel::class.java) }
        viewModel?.newNextMatchesData?.observe(viewLifecycleOwner, Observer<Resource<List<MatchEntity>?>> {
            checkState(view, it)
        })
    }

    private fun getFavoritePrevListData(view: View) {
        val viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteMatchViewModel::class.java)
        viewModel.getAllFavorite().observe(viewLifecycleOwner, Observer<List<MatchEntity>> {

            val newList = mutableListOf<MatchEntity>()
            for (item in it) {
                if(compareDateBeforeAndEqual(item.dateEvent)){
                    newList.add(item)
                }
            }

            initializeUi(view, newList)
        })
    }

    private fun getFavoriteNextListData(view: View) {
        val viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteMatchViewModel::class.java)
        viewModel.getAllFavorite().observe(viewLifecycleOwner, Observer<List<MatchEntity>> {

            val newList = mutableListOf<MatchEntity>()
            for (item in it) {
                if(compareDateAfter(item.dateEvent)){
                    newList.add(item)
                }
            }

            initializeUi(view, newList)
        })
    }

    private fun checkState(view: View, it: Resource<List<MatchEntity>?>){
        when (it.status) {
            SUCCESS -> {
                initializeUi(view, it.data)
            }
            ERROR -> toast(it.message.toString())
        }
    }

    private fun initializeUi(view: View, it: List<MatchEntity>?) {
        if (it != null) {
            if(it.isNotEmpty()){
                matchsData = ArrayList(it)

                linearLayoutManager = LinearLayoutManager(view.context)
                rv_category.layoutManager = linearLayoutManager
                rv_category.setHasFixedSize(true)

                matchAdapter = MatchAdapter(it) { matchModel ->
                    startActivity(intentFor<MatchDetailsActivity>(PARCELABLE_MATCH_DATA to matchModel))
                }
                matchAdapter.notifyDataSetChanged()
                rv_category.adapter = matchAdapter
            } else {
                no_data.visibility = View.VISIBLE
            }
        }

        shimmer_view_container.visibility = View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(ARG_SAVE_DATA, matchsData)
    }
}