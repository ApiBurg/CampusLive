package ru.campus.live.core.di.module

import dagger.Binds
import dagger.Module
import ru.campus.live.core.di.module.viewmodel.StartVModule
import ru.campus.live.start.data.repository.IStartRepository
import ru.campus.live.start.data.repository.StartRepository

@Module(includes = [StartBindModule::class, StartVModule::class])
class StartModule

@Module
interface StartBindModule {
    @Binds
    fun bindStartRepository(startRepository: StartRepository): IStartRepository
}

