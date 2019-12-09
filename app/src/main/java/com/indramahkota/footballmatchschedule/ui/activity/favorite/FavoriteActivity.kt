package com.indramahkota.footballmatchschedule.ui.activity.favorite

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.ui.fragment.match.MatchFragment
import com.indramahkota.footballmatchschedule.ui.fragment.match.adapter.TabPagerAdapter
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listTitle = arrayOf( resources.getString(R.string.prev_matches), resources.getString(R.string.next_matches) )

        val listFragment = mutableListOf(
            MatchFragment.newInstance(resources.getString(R.string.prev_favorite_matches_fragment)),
            MatchFragment.newInstance(resources.getString(R.string.next_favorite_matches_fragment))
        )

        val tabPagerAdapter = TabPagerAdapter(supportFragmentManager, listFragment, listTitle)
        view_pager.adapter = tabPagerAdapter
        tabs.setupWithViewPager(view_pager)
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