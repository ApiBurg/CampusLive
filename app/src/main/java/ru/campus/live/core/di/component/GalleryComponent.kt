package ru.campus.live.core.di.component

import dagger.Component
import ru.campus.live.core.di.AppDeps
import ru.campus.live.core.di.module.GalleryModule
import ru.campus.live.core.di.module.viewmodel.GalleryVModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelFactory

@Component(
    modules = [GalleryModule::class, GalleryVModule::class],
    dependencies = [AppDeps::class]
)
interface GalleryComponent {

    fun viewModelsFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        fun deps(appDeps: AppDeps): Builder
        fun build(): GalleryComponent
    }

}