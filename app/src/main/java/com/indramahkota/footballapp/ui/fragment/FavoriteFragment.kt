package com.indramahkota.footballapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.ui.activity.DetailsMatchActivity
import com.indramahkota.footballapp.ui.activity.DetailsMatchActivity.Companion.PARCELABLE_MATCH_DATA
import com.indramahkota.footballapp.ui.adapter.MatchVerticalAdapter
import com.indramahkota.footballapp.utilities.Utilities.compareDateAfter
import com.indramahkota.footballapp.utilities.Utilities.compareDateBeforeAndEqual
import com.indramahkota.footballapp.viewmodel.FavoriteViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_favorite.*
import javax.inject.Inject

class FavoriteFragment : DaggerFragment() {
    companion object {
        private const val ARG_SECTION_FRAGMENT = "section_fragment"
        private const val ARG_SAVE_DATA = "save_data"

        fun newInstance(fragment: String): FavoriteFragment {
            return FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SECTION_FRAGMENT, fragment)
                }
            }
        }
    }

    private var state: String? = null
    private var matchsData: ArrayList<MatchEntity>? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var matchAdapter: MatchVerticalAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        matchsData = savedInstanceState?.getParcelableArrayList(ARG_SAVE_DATA)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        state = arguments?.getString(ARG_SECTION_FRAGMENT)

        linearLayoutManager = LinearLayoutManager(view.context)
        rv_match.layoutManager = linearLayoutManager
        rv_match.setHasFixedSize(true)

        val listData = mutableListOf<MatchEntity>()
        matchAdapter =
            MatchVerticalAdapter(
                listData
            ) { matchModel ->
                run {
                    val intent = Intent(activity, DetailsMatchActivity::class.java).apply {
                        putExtra(PARCELABLE_MATCH_DATA, matchModel)
                    }
                    startActivity(intent)
                }
            }
        rv_match.adapter = matchAdapter

        if (matchsData != null) {
            initializeUi(matchsData)
        } else {
            when (state) {
                resources.getString(R.string.prev_favorite_match_fragment) -> getFavoritePrevListData()
                resources.getString(R.string.next_favorite_match_fragment) -> getFavoriteNextListData()
            }
        }
    }

    private fun getFavoritePrevListData() {
        val viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)
        viewModel.getAllFavoriteMatch().observe(viewLifecycleOwner, {
            val newList = ArrayList<MatchEntity>()
            for (item in it) {
                if (compareDateBeforeAndEqual(item.dateEvent)) {
                    newList.add(item)
                }
            }
            initializeUi(newList)
        })
    }

    private fun getFavoriteNextListData() {
        val viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)
        viewModel.getAllFavoriteMatch().observe(viewLifecycleOwner, {
            val newList = ArrayList<MatchEntity>()
            for (item in it) {
                if (compareDateAfter(item.dateEvent)) {
                    newList.add(item)
                }
            }
            initializeUi(newList)
        })
    }

    private fun initializeUi(it: List<MatchEntity>?) {
        if (it.isNullOrEmpty()) {
            no_data.visibility = View.VISIBLE
        } else {
            no_data.visibility = View.INVISIBLE
        }

        matchsData = it?.let { ArrayList(it) }
        it?.let { matchAdapter.replace(it) }

        shimmer_view_container.visibility = View.GONE
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