package com.indramahkota.footballapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.intentFor

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(intentFor<MainActivity>())
        finish()
    }
}
