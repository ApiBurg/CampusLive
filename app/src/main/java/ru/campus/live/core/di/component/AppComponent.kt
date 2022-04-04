package ru.campus.live.core.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.datasource.ErrorDataSource
import ru.campus.live.core.data.datasource.StringProvider
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.core.di.module.AppModule
import ru.campus.live.feed.db.AppDatabase

@Component(modules = [AppModule::class])
interface AppComponent {

    fun apiService(): APIService
    fun stringProvider(): StringProvider
    fun userDataSource(): UserDataSource
    fun context(): Context
    fun errorDataSource(): ErrorDataSource
    fun appDatabase(): AppDatabase

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }

}