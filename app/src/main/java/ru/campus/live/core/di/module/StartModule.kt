package ru.campus.live.core.di.module

import dagger.Module
import dagger.Provides
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.datasource.ErrorDataSource
import ru.campus.live.core.data.datasource.StringProvider
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.start.data.repository.IStartRepository
import ru.campus.live.start.data.repository.StartRepository
import ru.campus.live.start.domain.StartInteractor

@Module
class StartModule {

    @Provides
    fun provideStartRepository(
        apiService: APIService, errorDataSource: ErrorDataSource,
        userDataSource: UserDataSource, stringProvider: StringProvider
    ): StartRepository {
        return StartRepository(apiService, errorDataSource, userDataSource, stringProvider)
    }

    @Provides
    fun provideIStartRepository(startRepository: StartRepository): IStartRepository {
        return startRepository
    }

    @Provides
    fun provideStartInteractor(startRepository: IStartRepository): StartInteractor {
        return StartInteractor(startRepository)
    }

}

