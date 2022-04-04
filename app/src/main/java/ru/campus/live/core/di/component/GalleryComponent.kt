package ru.campus.live.core.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.campus.live.core.di.module.GalleryModule
import ru.campus.live.core.di.module.viewmodel.GalleryVModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelFactory

@Component(modules = [GalleryModule::class, GalleryVModule::class])
interface GalleryComponent {

    fun viewModelsFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): GalleryComponent
    }

}