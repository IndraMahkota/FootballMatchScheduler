package com.indramahkota.footballmatchschedule.di.module

import com.indramahkota.footballmatchschedule.data.source.FLeagueRepository
import com.indramahkota.footballmatchschedule.data.source.locale.database.MyDatabase
import com.indramahkota.footballmatchschedule.data.source.remote.api.ApiEndPoint
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(apiEndPoint: ApiEndPoint, myDataBase: MyDatabase): FLeagueRepository {
        return FLeagueRepository(apiEndPoint, myDataBase)
    }
}