package ru.campus.live.core.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.campus.live.core.data.APIService
import ru.campus.live.core.di.module.DiscussionModule
import ru.campus.live.core.di.module.viewmodel.DiscussionVModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelFactory

@Component(modules = [DiscussionModule::class, DiscussionVModule::class])
interface DiscussionComponent {

    fun viewModelsFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        @BindsInstance
        fun apiService(apiService: APIService): Builder
        fun build(): DiscussionComponent
    }

}