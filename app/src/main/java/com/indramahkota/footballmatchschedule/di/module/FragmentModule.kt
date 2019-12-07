package com.indramahkota.footballmatchschedule.di.module

import com.indramahkota.footballmatchschedule.ui.fragment.fragment.NextMatchesFragment
import com.indramahkota.footballmatchschedule.ui.fragment.fragment.PrevMatchesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun prevMatchesFragmentInjector(): PrevMatchesFragment?

    @ContributesAndroidInjector
    abstract fun nextMatchesFragmentInjector(): NextMatchesFragment?
}