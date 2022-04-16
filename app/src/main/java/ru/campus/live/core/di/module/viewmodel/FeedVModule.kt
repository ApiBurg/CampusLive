package ru.campus.live.core.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.campus.live.core.di.module.viewmodel.base.BaseViewModelModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelKey
import ru.campus.live.feed.presentation.viewmodel.CreatePublicationViewModel
import ru.campus.live.feed.presentation.viewmodel.FeedViewModel

@Module
abstract class FeedVModule : BaseViewModelModule() {

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    abstract fun feedVM(viewModel: FeedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreatePublicationViewModel::class)
    abstract fun createPublicationViewModel(viewModel: CreatePublicationViewModel): ViewModel

}