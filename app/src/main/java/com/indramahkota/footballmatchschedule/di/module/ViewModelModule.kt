package com.indramahkota.footballmatchschedule.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.indramahkota.footballmatchschedule.di.scope.ViewModelKey
import com.indramahkota.footballmatchschedule.di.factory.ViewModelFactory
import com.indramahkota.footballmatchschedule.viewmodel.LeagueViewModel
import com.indramahkota.footballmatchschedule.viewmodel.MatchDetailsViewModel
import com.indramahkota.footballmatchschedule.viewmodel.MatchViewModel
import com.indramahkota.footballmatchschedule.viewmodel.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory?): ViewModelProvider.Factory?

    @Binds
    @IntoMap
    @ViewModelKey(MatchViewModel::class)
    protected abstract fun matchViewModel(matchViewModel: MatchViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(LeagueViewModel::class)
    protected abstract fun leagueViewModel(leagueViewModel: LeagueViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(MatchDetailsViewModel::class)
    protected abstract fun matchDetailsViewModel(matchDetailsViewModel: MatchDetailsViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    protected abstract fun searchViewModel(searchViewModel: SearchViewModel?): ViewModel?
}