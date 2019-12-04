package com.indramahkota.footballmatchschedule

import android.app.Application
import com.indramahkota.footballmatchschedule.di.component.AppComponent
import com.indramahkota.footballmatchschedule.di.component.DaggerAppComponent
import com.indramahkota.footballmatchschedule.di.module.ApiModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class FootballLeagueApp : Application(), HasAndroidInjector {

    companion object {
        lateinit var component: AppComponent
    }

    @Inject
    lateinit var dispatchingAndroidInjector : DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
            .application(this)
            .apiModule(ApiModule())
            .build()
        component.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any>  = dispatchingAndroidInjector
}