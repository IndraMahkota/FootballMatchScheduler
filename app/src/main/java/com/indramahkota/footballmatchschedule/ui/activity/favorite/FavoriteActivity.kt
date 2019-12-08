package com.indramahkota.footballmatchschedule.ui.activity.favorite

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
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
            MatchFragment.newInstance(resources.getString(R.string.prev_matches_fragment)),
            MatchFragment.newInstance(resources.getString(R.string.next_matches_fragment))
        )

        val tabPagerAdapter = TabPagerAdapter(supportFragmentManager, listFragment, listTitle)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = tabPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
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