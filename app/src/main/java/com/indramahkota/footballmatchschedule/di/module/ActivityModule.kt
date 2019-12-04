package com.indramahkota.footballmatchschedule.di.module

import com.indramahkota.footballmatchschedule.ui.detail.MatchDetailsActivity
import com.indramahkota.footballmatchschedule.ui.match.MatchActivity
import com.indramahkota.footballmatchschedule.ui.search.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun bindMatchActivity (): MatchActivity

    @ContributesAndroidInjector
    abstract fun bindMatchDetailsActivity (): MatchDetailsActivity

    @ContributesAndroidInjector
    abstract fun bindMatchSearchActivity (): SearchActivity
}