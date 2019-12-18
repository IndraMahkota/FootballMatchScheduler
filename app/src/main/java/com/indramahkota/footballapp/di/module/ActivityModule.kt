package com.indramahkota.footballapp.di.module

import com.indramahkota.footballapp.ui.activity.detail.match.MatchDetailsActivity
import com.indramahkota.footballapp.ui.activity.detail.team.TeamDetailsActivity
import com.indramahkota.footballapp.ui.activity.match.MatchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun bindMatchActivity (): MatchActivity

    @ContributesAndroidInjector
    abstract fun bindMatchDetailsActivity (): MatchDetailsActivity

    @ContributesAndroidInjector
    abstract fun bindTeamDetailsActivity (): TeamDetailsActivity
}