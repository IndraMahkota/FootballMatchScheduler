package com.indramahkota.footballapp

import com.indramahkota.footballapp.di.component.DaggerAppComponent
import dagger.android.DaggerApplication

class FootballApp : DaggerApplication() {
    override fun applicationInjector() = DaggerAppComponent.builder().application(this).build()
}