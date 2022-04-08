package ru.campus.live.core.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.campus.live.core.di.module.viewmodel.LocationVModule
import ru.campus.live.location.data.repository.ILocationRepository
import ru.campus.live.location.data.repository.LocationRepository
import ru.campus.live.location.domain.LocationInteractor

@Module(includes = [LocationBindModule::class, LocationVModule::class])
class LocationModule {

    @Provides
    fun provideLocationInteractor(iLocationRepository: ILocationRepository): LocationInteractor {
        return LocationInteractor(iLocationRepository)
    }

}

@Module
interface LocationBindModule {

    @Binds
    fun bindLocationRepository(locationRepository: LocationRepository): ILocationRepository

}