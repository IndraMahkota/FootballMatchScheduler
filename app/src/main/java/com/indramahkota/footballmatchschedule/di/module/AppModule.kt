package com.indramahkota.footballmatchschedule.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module(includes = [ViewModelModule::class])
abstract class AppModule {
    @Binds
    abstract fun provideContext(application: Application?): Context?
}