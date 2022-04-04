package ru.campus.live.core.di.module

import dagger.Module
import dagger.Provides
import ru.campus.live.core.data.datasource.ErrorDataSource
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.repository.IUploadMediaRepository
import ru.campus.live.core.data.repository.UploadMediaRepository
import ru.campus.live.core.domain.PreparationMediaUseCase
import ru.campus.live.discussion.data.repository.DiscussionRepository
import ru.campus.live.discussion.data.repository.IDiscussionRepository

@Module
class DiscussionModule {

    @Provides
    fun provideDiscussionRepository(
        apiService: APIService,
        errorDataSource: ErrorDataSource,
        userDataSource: UserDataSource
    ): DiscussionRepository {
        return DiscussionRepository(apiService, errorDataSource, userDataSource)
    }

    @Provides
    fun provideIDiscussionRepository(discussionRepository: DiscussionRepository): IDiscussionRepository {
        return discussionRepository
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