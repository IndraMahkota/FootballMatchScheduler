package com.indramahkota.footballmatchschedule.di.module

import com.indramahkota.footballmatchschedule.ui.match.fragment.NextMatchesFragment
import com.indramahkota.footballmatchschedule.ui.match.fragment.PrevMatchesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun prevMatchesFragmentInjector(): PrevMatchesFragment?

    @ContributesAndroidInjector
    abstract fun nextMatchesFragmentInjector(): NextMatchesFragment?
}