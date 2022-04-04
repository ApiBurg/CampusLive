package ru.campus.live.core.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.campus.live.core.data.datasource.UserDataSource

@Module
class MainModule {

    @Provides
    fun providerUserDataSource(context: Context): UserDataSource {
        return UserDataSource(context)
    }

}