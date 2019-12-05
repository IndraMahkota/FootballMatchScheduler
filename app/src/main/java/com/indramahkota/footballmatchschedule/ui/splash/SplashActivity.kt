package com.indramahkota.footballmatchschedule.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.indramahkota.footballmatchschedule.ui.main.MainActivity
import org.jetbrains.anko.intentFor

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            Handler().postDelayed({
                startActivity(intentFor<MainActivity>())
                finish()
            }, 2000)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("STATE_HANDLER", true)
    }

    override fun onBackPressed() {}
}
