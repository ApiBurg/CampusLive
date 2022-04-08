package ru.campus.live.core.di.component

import dagger.Component
import ru.campus.live.core.di.deps.AppDeps
import ru.campus.live.core.di.module.DiscussionModule
import ru.campus.live.core.di.module.viewmodel.DiscussionVModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelFactory

@Component(
    modules = [DiscussionModule::class, DiscussionVModule::class],
    dependencies = [AppDeps::class]
)
interface DiscussionComponent {

    fun viewModelsFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        fun deps(appDeps: AppDeps): Builder
        fun build(): DiscussionComponent
    }

}