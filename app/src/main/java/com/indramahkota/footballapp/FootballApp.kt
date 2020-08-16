package com.indramahkota.footballapp

import android.app.Application
import com.indramahkota.footballapp.di.component.AppComponent
import com.indramahkota.footballapp.di.component.DaggerAppComponent
import com.indramahkota.footballapp.di.module.ApiModule
import com.indramahkota.footballapp.di.module.DbModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class FootballApp : Application(), HasAndroidInjector {

    companion object {
        lateinit var component: AppComponent
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
            .application(this)
            .apiModule(ApiModule())
            .dbModule(DbModule())
            .build()
        component.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}