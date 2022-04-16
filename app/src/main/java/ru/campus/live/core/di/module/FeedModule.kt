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
import ru.campus.live.discussion.domain.usecase.DiscussionTitleUseCase
import ru.campus.live.ribbon.data.repository.IRibbonRepository
import ru.campus.live.ribbon.data.repository.RibbonRepository
import ru.campus.live.ribbon.domain.RibbonInteractor

@Module(includes = [FeedBindModule::class, FeedVModule::class])
class FeedModule {

    @Provides
    fun provideDisplayMetrics(context: Context): DisplayMetrics {
        return DisplayMetrics(context)
    }

}

@Module
interface FeedBindModule {
    @Binds
    fun bindWallRepository(ribbonRepository: RibbonRepository): IRibbonRepository

    @Binds
    fun bindUploadMediaRepository(uploadMediaRepository: UploadMediaRepository): IUploadMediaRepository
}