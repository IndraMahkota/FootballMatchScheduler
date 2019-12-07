package com.indramahkota.footballmatchschedule.di.module

import com.indramahkota.footballmatchschedule.ui.activity.detail.MatchDetailsActivity
import com.indramahkota.footballmatchschedule.ui.activity.match.MatchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun bindMatchActivity (): MatchActivity

    @ContributesAndroidInjector
    abstract fun bindMatchDetailsActivity (): MatchDetailsActivity
}