package ru.campus.live.core.di.component

import dagger.BindsInstance
import dagger.Component
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.datasource.ErrorDataSource
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.core.di.module.LocationModule
import ru.campus.live.core.di.module.viewmodel.LocationVModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelFactory

@Component(modules = [LocationModule::class, LocationVModule::class])
interface LocationComponent {

    fun viewModelsFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun apiService(apiService: APIService): Builder

        @BindsInstance
        fun userDataSource(userDataSource: UserDataSource): Builder

        @BindsInstance
        fun errorDataSource(errorDataSource: ErrorDataSource): Builder
        fun build(): LocationComponent
    }

}