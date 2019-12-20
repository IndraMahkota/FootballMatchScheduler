package com.indramahkota.footballapp.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.ui.fragment.FavoriteFragment
import com.indramahkota.footballapp.ui.fragment.TeamFragment
import com.indramahkota.footballapp.ui.pager.TabPagerAdapter
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listTitle = arrayOf(
            resources.getString(R.string.prev_match),
            resources.getString(R.string.next_match),
            resources.getString(R.string.team) )

        val listFragment = mutableListOf(
            FavoriteFragment.newInstance(resources.getString(R.string.prev_favorite_match_fragment)),
            FavoriteFragment.newInstance(resources.getString(R.string.next_favorite_match_fragment)),
            TeamFragment.newInstance(resources.getString(R.string.favorite_team_fragment))
        )

        val tabPagerAdapter =
            TabPagerAdapter(
                supportFragmentManager,
                listFragment,
                listTitle
            )
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