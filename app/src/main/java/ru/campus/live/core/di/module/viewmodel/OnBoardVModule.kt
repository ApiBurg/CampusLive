package ru.campus.live.core.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.campus.live.core.di.module.viewmodel.base.BaseViewModelModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelKey
import ru.campus.live.start.viewmodel.StartViewModel

@Module
abstract class OnBoardVModule : BaseViewModelModule() {

    @Binds
    @IntoMap
    @ViewModelKey(StartViewModel::class)
    abstract fun onBoardViewModel(viewModel: StartViewModel): ViewModel

}