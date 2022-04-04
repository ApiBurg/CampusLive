package ru.campus.live.core.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.campus.live.core.di.module.MainModule
import ru.campus.live.core.di.module.viewmodel.MainVModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelFactory

@Component(modules = [MainModule::class, MainVModule::class])
interface MainComponent {

    fun viewModelsFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): MainComponent
    }

}