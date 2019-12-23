package com.indramahkota.footballapp.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.indramahkota.footballapp.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_match_details.*

class DetailsTeamActivity : AppCompatActivity() {

    companion object {
        const val PARCELABLE_TEAM_DATA = "parcelable_team_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_details)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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