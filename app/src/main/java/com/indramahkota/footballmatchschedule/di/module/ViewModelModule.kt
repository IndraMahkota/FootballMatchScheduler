package com.indramahkota.footballmatchschedule.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.indramahkota.footballmatchschedule.di.scope.ViewModelKey
import com.indramahkota.footballmatchschedule.di.factory.ViewModelFactory
import com.indramahkota.footballmatchschedule.viewmodel.MatchDetailsViewModel
import com.indramahkota.footballmatchschedule.viewmodel.LeagueDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory?): ViewModelProvider.Factory?

    @Binds
    @IntoMap
    @ViewModelKey(LeagueDetailsViewModel::class)
    protected abstract fun matchViewModel(leagueDetailsViewModel: LeagueDetailsViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(MatchDetailsViewModel::class)
    protected abstract fun matchDetailsViewModel(matchDetailsViewModel: MatchDetailsViewModel?): ViewModel?
}