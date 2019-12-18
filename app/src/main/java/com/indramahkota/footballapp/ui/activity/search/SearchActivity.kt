package com.indramahkota.footballapp.ui.activity.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.data.source.locale.entity.MatchEntity
import com.indramahkota.footballapp.ui.activity.detail.match.MatchDetailsActivity
import com.indramahkota.footballapp.ui.activity.detail.match.MatchDetailsActivity.Companion.PARCELABLE_MATCH_DATA
import com.indramahkota.footballapp.ui.adapter.match.MatchAdapter
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.intentFor
import java.util.*

class SearchActivity : AppCompatActivity() {

    companion object {
        const val PARCELABLE_DATA = "parcelable_data"
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var matchAdapter: MatchAdapter

    private var listData: List<MatchEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        shimmer_view_container.visibility = View.GONE

        listData = intent?.getParcelableArrayListExtra(PARCELABLE_DATA)

        linearLayoutManager = LinearLayoutManager(this)
        rv_category.layoutManager = linearLayoutManager
        rv_category.setHasFixedSize(true)

        val rvData = mutableListOf<MatchEntity>()
        matchAdapter = MatchAdapter(rvData) { matchModel ->
                startActivity(intentFor<MatchDetailsActivity>(PARCELABLE_MATCH_DATA to matchModel))
        }
        rv_category.adapter = matchAdapter

        setRecycleView()
    }

    private fun setRecycleView(query:String? = null){
        var newData:List<MatchEntity>? = null

        if(query != null && query.isNotEmpty()) {
            newData = listData?.filter {
                it.strHomeTeam.toLowerCase(Locale.getDefault()).contains(query) ||
                        it.strAwayTeam.toLowerCase(Locale.getDefault()).contains(query)
            }
        }

        if(newData.isNullOrEmpty()) {
            no_data.visibility = View.VISIBLE
        } else {
            no_data.visibility = View.INVISIBLE
        }

        newData?.let { matchAdapter.replace(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu?.findItem(R.id.search_menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.isFocusable = true
        searchView.isIconified = false
        searchView.requestFocusFromTouch()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                setRecycleView(query.toLowerCase(Locale.getDefault()))
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                setRecycleView(query.toLowerCase(Locale.getDefault()))
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
