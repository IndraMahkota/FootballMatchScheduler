package com.indramahkota.footballmatchschedule.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.indramahkota.footballmatchschedule.di.scope.ViewModelKey
import com.indramahkota.footballmatchschedule.di.factory.ViewModelFactory
import com.indramahkota.footballmatchschedule.viewmodel.LeagueDetailsViewModel
import com.indramahkota.footballmatchschedule.viewmodel.MatchDetailsViewModel
import com.indramahkota.footballmatchschedule.viewmodel.MatchListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory?): ViewModelProvider.Factory?

    @Binds
    @IntoMap
    @ViewModelKey(MatchListViewModel::class)
    protected abstract fun matchViewModel(matchListViewModel: MatchListViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(LeagueDetailsViewModel::class)
    protected abstract fun leagueViewModel(leagueDetailsViewModel: LeagueDetailsViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(MatchDetailsViewModel::class)
    protected abstract fun matchDetailsViewModel(matchDetailsViewModel: MatchDetailsViewModel?): ViewModel?
}