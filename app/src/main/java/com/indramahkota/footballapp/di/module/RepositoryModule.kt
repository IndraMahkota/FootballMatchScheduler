package com.indramahkota.footballapp.di.module

import com.indramahkota.footballapp.data.source.FootballAppRepository
import com.indramahkota.footballapp.data.source.locale.database.MyDatabase
import com.indramahkota.footballapp.data.source.remote.api.ApiEndPoint
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(apiEndPoint: ApiEndPoint, myDataBase: MyDatabase): FootballAppRepository {
        return FootballAppRepository(apiEndPoint, myDataBase)
    }
}