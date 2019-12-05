package com.indramahkota.footballmatchschedule.ui.search

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.remote.model.MatchModel
import com.indramahkota.footballmatchschedule.ui.detail.MatchDetailsActivity
import com.indramahkota.footballmatchschedule.ui.detail.MatchDetailsActivity.Companion.PARCELABLE_MATCH_DATA
import com.indramahkota.footballmatchschedule.ui.match.adapter.MatchAdapter
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.intentFor

class SearchActivity : AppCompatActivity() {

    companion object {
        const val STRING_DATA = "string_data"
        const val PARCELABLE_DATA = "parcelable_data"
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var matchAdapter: MatchAdapter

    private var query: String = ""
    private var allData = arrayListOf<MatchModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        query = intent.getStringExtra(STRING_DATA)!!
        allData = ArrayList(intent.getParcelableArrayListExtra(PARCELABLE_DATA)!!)

        Log.d("HHHH", allData.size.toString()+"3")

        linearLayoutManager = LinearLayoutManager(this)
        rv_category.layoutManager = linearLayoutManager
        rv_category.setHasFixedSize(true)

        matchAdapter = MatchAdapter(allData){ matchModel ->
            startActivity(intentFor<MatchDetailsActivity>(PARCELABLE_MATCH_DATA to matchModel))
        }
        rv_category.adapter = matchAdapter
        //no_data.visibility = View.VISIBLE
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
