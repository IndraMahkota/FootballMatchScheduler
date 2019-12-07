package com.indramahkota.footballmatchschedule.ui.activity.search

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.locale.entity.MatchEntity
import com.indramahkota.footballmatchschedule.ui.activity.detail.MatchDetailsActivity
import com.indramahkota.footballmatchschedule.ui.activity.detail.MatchDetailsActivity.Companion.PARCELABLE_MATCH_DATA
import com.indramahkota.footballmatchschedule.ui.fragment.adapter.MatchAdapter
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.intentFor
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {

    companion object {
        const val STRING_DATA = "string_data"
        const val PARCELABLE_DATA = "parcelable_data"
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var matchAdapter: MatchAdapter

    private var query: String = ""
    private var allData = arrayListOf<MatchEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        query = intent.getStringExtra(STRING_DATA)!!
        allData = ArrayList(intent.getParcelableArrayListExtra(PARCELABLE_DATA)!!)

        val newData = allData.filter {
            it.strHomeTeam.toLowerCase(Locale.getDefault()).contains(query) ||
                    it.strAwayTeam.toLowerCase(Locale.getDefault()).contains(query)
        }

        if(newData.isNotEmpty()) {
            linearLayoutManager = LinearLayoutManager(this)
            rv_category.layoutManager = linearLayoutManager
            rv_category.setHasFixedSize(true)

            matchAdapter = MatchAdapter(newData){ matchModel ->
                startActivity(intentFor<MatchDetailsActivity>(PARCELABLE_MATCH_DATA to matchModel))
            }
            matchAdapter.notifyDataSetChanged()
            rv_category.adapter = matchAdapter
        } else {
            no_data.visibility = View.VISIBLE
        }

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
