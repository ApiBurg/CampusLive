package ru.campus.live.core.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.campus.live.core.di.module.viewmodel.StartVModule
import ru.campus.live.start.data.repository.IStartRepository
import ru.campus.live.start.data.repository.StartRepository
import ru.campus.live.start.domain.StartInteractor

@Module(includes = [StartBindModule::class, StartVModule::class])
class StartModule {

    @Provides
    fun provideStartInteractor(startRepository: IStartRepository): StartInteractor {
        return StartInteractor(startRepository)
    }

}

@Module
interface StartBindModule {
    @Binds
    fun bindStartRepository(startRepository: StartRepository): IStartRepository
}

