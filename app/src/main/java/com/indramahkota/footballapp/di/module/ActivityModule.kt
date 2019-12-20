package com.indramahkota.footballapp.di.module

import com.indramahkota.footballapp.ui.activity.DetailsMatchActivity
import com.indramahkota.footballapp.ui.activity.DetailsTeamActivity
import com.indramahkota.footballapp.ui.activity.MatchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun bindMatchActivity (): MatchActivity

    @ContributesAndroidInjector
    abstract fun bindMatchDetailsActivity (): DetailsMatchActivity

    @ContributesAndroidInjector
    abstract fun bindTeamDetailsActivity (): DetailsTeamActivity
}