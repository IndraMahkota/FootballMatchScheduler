package com.indramahkota.footballmatchschedule.di.component

import android.app.Application
import com.indramahkota.footballmatchschedule.FootballLeagueApp
import com.indramahkota.footballmatchschedule.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        RepositoryModule::class,
        ApiModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelModule::class
    ]
)

interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application:Application): Builder
        @BindsInstance
        fun apiModule(apiModule: ApiModule): Builder
        fun build(): AppComponent
    }

    fun inject(footballLeagueApp: FootballLeagueApp)
}
