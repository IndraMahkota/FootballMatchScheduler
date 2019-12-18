package com.indramahkota.footballapp.di.module

import com.indramahkota.footballapp.ui.fragment.favorite.FavoriteFragment
import com.indramahkota.footballapp.ui.fragment.match.MatchFragment
import com.indramahkota.footballapp.ui.fragment.standing.StandingFragment
import com.indramahkota.footballapp.ui.fragment.team.TeamFragment
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