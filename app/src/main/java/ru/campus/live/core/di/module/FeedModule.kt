package ru.campus.live.core.di.module

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.campus.live.core.data.datasource.DisplayMetrics
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.core.data.repository.IUploadMediaRepository
import ru.campus.live.core.data.repository.UploadMediaRepository
import ru.campus.live.core.di.module.viewmodel.FeedVModule
import ru.campus.live.feed.data.repository.IWallRepository
import ru.campus.live.feed.data.repository.WallRepository
import ru.campus.live.feed.domain.FeedInteractor

@Module(includes = [FeedBindModule::class, FeedVModule::class])
class FeedModule {

    @Provides
    fun provideFeedInteractor(
        iWallRepository: IWallRepository,
        userDataSource: UserDataSource,
        uploadMediaRepository: IUploadMediaRepository,
        displayMetrics: DisplayMetrics
    ): FeedInteractor {
        return FeedInteractor(
            iWallRepository,
            userDataSource,
            uploadMediaRepository,
            displayMetrics
        )
    }

    @Provides
    fun provideDisplayMetrics(context: Context): DisplayMetrics {
        return DisplayMetrics(context)
    }

}

@Module
interface FeedBindModule {
    @Binds
    fun bindWallRepository(wallRepository: WallRepository): IWallRepository

    @Binds
    fun bindUploadMediaRepository(uploadMediaRepository: UploadMediaRepository): IUploadMediaRepository
}