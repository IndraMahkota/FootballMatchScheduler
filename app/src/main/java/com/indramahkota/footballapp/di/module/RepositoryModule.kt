package com.indramahkota.footballapp.di.module

import com.indramahkota.footballapp.data.source.repository.FootballAppRepository
import com.indramahkota.footballapp.data.source.locale.database.AppDao
import com.indramahkota.footballapp.data.source.remote.EndPointService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(service: EndPointService, dao: AppDao): FootballAppRepository =
        FootballAppRepository(
            service,
            dao
        )
}