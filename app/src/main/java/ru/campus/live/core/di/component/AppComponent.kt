package ru.campus.live.core.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.datasource.ErrorDataSource
import ru.campus.live.core.data.datasource.StringProvider
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.core.di.AppDependencies
import ru.campus.live.core.di.module.AppModule
import ru.campus.live.feed.db.AppDatabase

@Component(modules = [AppModule::class])
interface AppComponent : AppDependencies {

    override val context: Context
    override val apiService: APIService
    override val appDatabase: AppDatabase

    fun stringProvider(): StringProvider
    fun userDataSource(): UserDataSource
    fun errorDataSource(): ErrorDataSource

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }

}