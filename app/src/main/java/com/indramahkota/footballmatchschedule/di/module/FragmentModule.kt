package com.indramahkota.footballmatchschedule.di.module

import com.indramahkota.footballmatchschedule.ui.fragment.match.MatchFragment
import com.indramahkota.footballmatchschedule.ui.fragment.standing.StandingFragment
import com.indramahkota.footballmatchschedule.ui.fragment.team.TeamFragment
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
}