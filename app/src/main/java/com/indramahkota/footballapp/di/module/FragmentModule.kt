package com.indramahkota.footballapp.di.module

import com.indramahkota.footballapp.ui.fragment.FavoriteFragment
import com.indramahkota.footballapp.ui.fragment.MatchFragment
import com.indramahkota.footballapp.ui.fragment.StandingFragment
import com.indramahkota.footballapp.ui.fragment.TeamFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun matchesFragmentInjector(): MatchFragment?

    @ContributesAndroidInjector
    abstract fun standingFragmentInjector(): StandingFragment?

    @ContributesAndroidInjector
    abstract fun teamFragmentInjector(): TeamFragment?

    @ContributesAndroidInjector
    abstract fun favoriteFragmentInjector(): FavoriteFragment?
}