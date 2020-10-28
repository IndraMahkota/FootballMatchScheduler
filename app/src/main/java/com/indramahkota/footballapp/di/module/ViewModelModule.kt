package com.indramahkota.footballapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.indramahkota.footballapp.di.scope.ViewModelKey
import com.indramahkota.footballapp.di.factory.ViewModelFactory
import com.indramahkota.footballapp.viewmodel.FavoriteViewModel
import com.indramahkota.footballapp.viewmodel.MatchDetailsViewModel
import com.indramahkota.footballapp.viewmodel.LeagueDetailsViewModel
import com.indramahkota.footballapp.viewmodel.TeamDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LeagueDetailsViewModel::class)
    protected abstract fun leagueDetailsViewModel(viewModel: LeagueDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MatchDetailsViewModel::class)
    protected abstract fun matchDetailsViewModel(viewModel: MatchDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    protected abstract fun favoriteViewModel(viewModel: FavoriteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TeamDetailsViewModel::class)
    protected abstract fun teamDetailsViewModel(viewModel: TeamDetailsViewModel): ViewModel
}