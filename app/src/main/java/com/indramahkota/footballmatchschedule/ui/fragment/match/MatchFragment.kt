package com.indramahkota.footballmatchschedule.ui.fragment.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.Status.ERROR
import com.indramahkota.footballmatchschedule.data.source.Status.SUCCESS
import com.indramahkota.footballmatchschedule.data.source.locale.entity.MatchEntity
import com.indramahkota.footballmatchschedule.ui.activity.detail.match.MatchDetailsActivity
import com.indramahkota.footballmatchschedule.ui.activity.detail.match.MatchDetailsActivity.Companion.PARCELABLE_MATCH_DATA
import com.indramahkota.footballmatchschedule.ui.adapter.match.MatchAdapter
import com.indramahkota.footballmatchschedule.utilities.Utilities.compareDateAfter
import com.indramahkota.footballmatchschedule.utilities.Utilities.compareDateBeforeAndEqual
import com.indramahkota.footballmatchschedule.viewmodel.FavoriteMatchViewModel
import com.indramahkota.footballmatchschedule.viewmodel.LeagueDetailsViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_match.*
import kotlinx.android.synthetic.main.fragment_match.view.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

class MatchFragment : Fragment() {
    companion object {
        private const val ARG_SECTION_FRAGMENT = "section_fragment"
        private const val ARG_SAVE_DATA = "save_data"

        @JvmStatic
        fun newInstance(fragment: String) = MatchFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_SECTION_FRAGMENT, fragment)
            }
        }
    }

    private var state: String? = null
    private var matchsData: ArrayList<MatchEntity>? = null
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
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        state = arguments?.getString(ARG_SECTION_FRAGMENT)

        frame_prev.rv_prev_match.setHasFixedSize(true)

        val listData = mutableListOf<MatchEntity>()
        matchAdapter = MatchAdapter(listData) { matchModel ->
                startActivity(intentFor<MatchDetailsActivity>(PARCELABLE_MATCH_DATA to matchModel))
        }
        frame_prev.rv_prev_match.adapter = matchAdapter

        if(matchsData != null){
            initializeUi(matchsData)
        } else {
            when (state) {
                resources.getString(R.string.prev_match_fragment) -> getPrevListData()
                resources.getString(R.string.next_match_fragment) -> getNextListData()
                resources.getString(R.string.prev_favorite_match_fragment) -> getFavoritePrevListData()
                resources.getString(R.string.next_favorite_match_fragment) -> getFavoriteNextListData()
            }
        }
    }

    private fun getPrevListData() {
        val viewModel = activity?.let { ViewModelProvider(it, viewModelFactory).get(LeagueDetailsViewModel::class.java) }
        viewModel?.newPrevMatchData?.observe(viewLifecycleOwner, Observer<Resource<List<MatchEntity>?>> {
            checkState(it)
        })
    }

    private fun getNextListData() {
        val viewModel = activity?.let { ViewModelProvider(it, viewModelFactory).get(LeagueDetailsViewModel::class.java) }
        viewModel?.newNextMatchData?.observe(viewLifecycleOwner, Observer<Resource<List<MatchEntity>?>> {
            checkState(it)
        })
    }

    private fun getFavoritePrevListData() {
        val viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteMatchViewModel::class.java)
        viewModel.getAllFavorite().observe(viewLifecycleOwner, Observer<List<MatchEntity>> {
            val newList = ArrayList<MatchEntity>()
            for (item in it) {
                if(compareDateBeforeAndEqual(item.dateEvent)){
                    newList.add(item)
                }
            }
            initializeUi(newList)
        })
    }

    private fun getFavoriteNextListData() {
        val viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteMatchViewModel::class.java)
        viewModel.getAllFavorite().observe(viewLifecycleOwner, Observer<List<MatchEntity>> {
            val newList = ArrayList<MatchEntity>()
            for (item in it) {
                if(compareDateAfter(item.dateEvent)){
                    newList.add(item)
                }
            }
            initializeUi(newList)
        })
    }

    private fun checkState(it: Resource<List<MatchEntity>?>){
        when (it.status) {
            SUCCESS -> {
                initializeUi(it.data)
            }
            ERROR -> toast(it.message.toString())
        }
    }

    private fun initializeUi(it: List<MatchEntity>?) {
        if(it.isNullOrEmpty()) {
            no_data.visibility = View.VISIBLE
        } else {
            no_data.visibility = View.INVISIBLE
        }

        matchsData = it?.let { ArrayList(it) }
        it?.let { matchAdapter.replace(it) }

        frame_prev.prev_shimmer_view_container.visibility = View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(ARG_SAVE_DATA, matchsData)
    }

    override fun onResume() {
        super.onResume()

        when (state) {
            resources.getString(R.string.prev_favorite_match_fragment) -> getFavoritePrevListData()
            resources.getString(R.string.next_favorite_match_fragment) -> getFavoriteNextListData()
        }
    }
}