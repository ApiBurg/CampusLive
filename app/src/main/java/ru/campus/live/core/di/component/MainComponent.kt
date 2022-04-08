package ru.campus.live.core.di.component

import dagger.Component
import ru.campus.live.core.di.AppDeps
import ru.campus.live.core.di.module.MainModule
import ru.campus.live.core.di.module.viewmodel.MainVModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelFactory

@Component(modules = [MainModule::class, MainVModule::class], dependencies = [AppDeps::class])
interface MainComponent {

    fun viewModelsFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        fun deps(appDeps: AppDeps): Builder
        fun build(): MainComponent
    }

}