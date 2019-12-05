package com.indramahkota.footballmatchschedule.ui.search

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.ui.match.adapter.MatchAdapter
import kotlinx.android.synthetic.main.content_match_tab.*
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    companion object {
        const val STRING_DATA = "string_data"
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var matchAdapter: MatchAdapter

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //val searchQuery = intent.getStringExtra(STRING_DATA)!!

        linearLayoutManager = LinearLayoutManager(this)
        rv_category.layoutManager = linearLayoutManager
        rv_category.setHasFixedSize(true)

        matchAdapter = MatchAdapter(null){}
        rv_category.adapter = matchAdapter

        /*matchAdapter = MatchAdapter(searchResult){ matchDetailsApiModel ->
            startActivity(intentFor<MatchDetailsActivity>(PARCELABLE_MATCH_DATA to matchDetailsApiModel))
        }*/
        //rv_category.adapter = matchAdapter
        no_data.visibility = View.VISIBLE
        shimmer_view_container.visibility = View.GONE
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
