package com.indramahkota.footballmatchschedule.ui.activity.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.locale.entity.MatchEntity
import com.indramahkota.footballmatchschedule.ui.activity.detail.MatchDetailsActivity
import com.indramahkota.footballmatchschedule.ui.activity.detail.MatchDetailsActivity.Companion.PARCELABLE_MATCH_DATA
import com.indramahkota.footballmatchschedule.ui.fragment.match.adapter.MatchAdapter
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.intentFor
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {

    companion object {
        const val PARCELABLE_DATA = "parcelable_data"
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var matchAdapter: MatchAdapter

    private var listData = arrayListOf<MatchEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        shimmer_view_container.visibility = View.GONE

        listData = ArrayList(intent.getParcelableArrayListExtra(PARCELABLE_DATA)!!)

        linearLayoutManager = LinearLayoutManager(this)
        rv_category.layoutManager = linearLayoutManager
        rv_category.setHasFixedSize(true)
        setRecycleView()
    }

    private fun setRecycleView(query:String? = null){
        var newData:List<MatchEntity> = mutableListOf()

        if(query != null && query.isNotEmpty()) {
            newData = listData.filter {
                it.strHomeTeam.toLowerCase(Locale.getDefault()).contains(query) ||
                        it.strAwayTeam.toLowerCase(Locale.getDefault()).contains(query)
            }
        }

        matchAdapter = MatchAdapter(newData){ matchModel ->
            startActivity(intentFor<MatchDetailsActivity>(PARCELABLE_MATCH_DATA to matchModel))
        }
        matchAdapter.notifyDataSetChanged()
        rv_category.adapter = matchAdapter

        if(newData.isEmpty()) {
            no_data.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search_menu)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                setRecycleView(query)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                setRecycleView(query)
                return false
            }
        })

        return true
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
