package com.indramahkota.footballapp.di.component

import android.app.Application
import com.indramahkota.footballapp.FootballApp
import com.indramahkota.footballapp.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ApiModule::class,
        DbModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelModule::class,
        RepositoryModule::class
    ]
)

interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        @BindsInstance
        fun apiModule(apiModule: ApiModule): Builder

        @BindsInstance
        fun dbModule(dbModule: DbModule): Builder
        fun build(): AppComponent
    }

    fun inject(footballApp: FootballApp)
}
