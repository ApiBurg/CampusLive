package ru.campus.live.core.di.module

import dagger.Module
import dagger.Provides
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.datasource.ErrorDataSource
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.location.data.repository.ILocationRepository
import ru.campus.live.location.data.repository.LocationRepository
import ru.campus.live.location.domain.LocationInteractor

@Module
class LocationModule {

    @Provides
    fun provideLocationRepository(
        apiService: APIService,
        errorDataSource: ErrorDataSource,
        userDataSource: UserDataSource
    ): LocationRepository {
        return LocationRepository(apiService, errorDataSource, userDataSource)
    }

    @Provides
    fun provideILocationRepository(locationRepository: LocationRepository): ILocationRepository {
        return locationRepository
    }

    @Provides
    fun provideLocationInteractor(iLocationRepository: ILocationRepository): LocationInteractor {
        return LocationInteractor(iLocationRepository)
    }

}