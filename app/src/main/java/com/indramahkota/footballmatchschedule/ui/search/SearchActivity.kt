package com.indramahkota.footballmatchschedule.ui.search

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.Resource
import com.indramahkota.footballmatchschedule.data.source.Status
import com.indramahkota.footballmatchschedule.data.source.remote.apiresponse.SearchMatchsApiResponse
import com.indramahkota.footballmatchschedule.ui.detail.MatchDetailsActivity
import com.indramahkota.footballmatchschedule.ui.detail.MatchDetailsActivity.Companion.PARCELABLE_MATCH_DATA
import com.indramahkota.footballmatchschedule.ui.match.adapter.MatchAdapter
import com.indramahkota.footballmatchschedule.viewmodel.SearchMatchsViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.content_match_tab.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    companion object {
        const val STRING_DATA = "string_data"
    }

    private lateinit var viewModel: SearchMatchsViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var matchAdapter: MatchAdapter

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val searchQuery = intent.getStringExtra(STRING_DATA)!!

        linearLayoutManager = LinearLayoutManager(this)
        rv_category.layoutManager = linearLayoutManager
        rv_category.setHasFixedSize(true)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchMatchsViewModel::class.java)
        viewModel.searchEvent.observe(this, Observer<Resource<SearchMatchsApiResponse?>>{
            when (it.status) {
                Status.SUCCESS -> {
                    if(it.data?.event != null) {
                        val searchResult = it.data.event.filter { matchDetailsApiModel -> matchDetailsApiModel.strSport.equals("Soccer") }
                        matchAdapter = MatchAdapter(searchResult){ matchDetailsApiModel ->
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
        viewModel.searchEventByQuery(searchQuery)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
