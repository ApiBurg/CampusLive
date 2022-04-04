package ru.campus.live.core.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.campus.live.core.data.APIService
import ru.campus.live.core.di.module.FeedModule
import ru.campus.live.core.di.module.viewmodel.FeedVModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelFactory
import ru.campus.live.feed.db.AppDatabase

@Component(modules = [FeedModule::class, FeedVModule::class])
interface FeedComponent {

    fun viewModelsFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun appDatabase(appDatabase: AppDatabase): Builder

        @BindsInstance
        fun apiService(apiService: APIService): Builder
        fun build(): FeedComponent
    }


}