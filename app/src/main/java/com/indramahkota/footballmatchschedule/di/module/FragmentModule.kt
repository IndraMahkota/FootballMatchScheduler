package com.indramahkota.footballmatchschedule.di.module

import com.indramahkota.footballmatchschedule.ui.fragment.match.MatchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun matchesFragmentInjector(): MatchFragment?
}