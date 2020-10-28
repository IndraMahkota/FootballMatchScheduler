package com.indramahkota.footballapp.di.module

import android.app.Application
import com.indramahkota.footballapp.data.source.locale.database.AppDao
import com.indramahkota.footballapp.data.source.locale.database.MyDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): MyDatabase = MyDatabase.getDatabase(application)

    @Provides
    @Singleton
    fun provideFavoriteDao(myDatabase: MyDatabase): AppDao = myDatabase.appDao()
}