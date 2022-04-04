package ru.campus.live.core.di.module

import dagger.Module
import dagger.Provides
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.datasource.ErrorDataSource
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.core.data.datasource.interfaces.IUserDataSource
import ru.campus.live.core.data.repository.IUploadMediaRepository
import ru.campus.live.core.data.repository.UploadMediaRepository
import ru.campus.live.core.domain.PreparationMediaUseCase
import ru.campus.live.feed.data.repository.IWallRepository
import ru.campus.live.feed.data.repository.WallRepository
import ru.campus.live.feed.domain.FeedInteractor

@Module
class FeedModule {

    @Provides
    fun provideWallRepository(
        apiService: APIService,
        errorDataSource: ErrorDataSource,
        userDataSource: UserDataSource
    ): WallRepository {
        return WallRepository(apiService, errorDataSource, userDataSource)
    }

    @Provides
    fun provideIWallRepository(wallRepository: WallRepository): IWallRepository {
        return wallRepository
    }

    @Provides
    fun provideFeedInteractor(
        iWallRepository: IWallRepository,
        userDataSource: UserDataSource,
        uploadMediaRepository: IUploadMediaRepository
    ): FeedInteractor {
        return FeedInteractor(iWallRepository, userDataSource, uploadMediaRepository)
    }

    @Provides
    fun provideIUserDataSource(userDataSource: UserDataSource): IUserDataSource {
        return userDataSource
    }

    @Provides
    fun provideUploadMediaRepository(
        apiService: APIService,
        errorDataSource: ErrorDataSource,
        preparationMediaUseCase: PreparationMediaUseCase
    ): UploadMediaRepository {
        return UploadMediaRepository(apiService, errorDataSource, preparationMediaUseCase)
    }

    @Provides
    fun provideIUploadMediaRepository(uploadMediaRepository: UploadMediaRepository): IUploadMediaRepository {
        return uploadMediaRepository
    }

}