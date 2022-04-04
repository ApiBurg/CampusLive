package ru.campus.live.core.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.datasource.ErrorDataSource
import ru.campus.live.core.data.datasource.HostDataSource
import ru.campus.live.core.data.datasource.StringProvider
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.feed.db.AppDatabase

@Module
class AppModule {

    @Provides
    fun provideStringProvider(context: Context): StringProvider {
        return StringProvider(context = context)
    }

    @Provides
    fun provideAPIService(hostDataSource: HostDataSource): APIService {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(hostDataSource.domain())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(APIService::class.java)
    }

    @Provides
    fun provideUserDataSource(context: Context): UserDataSource {
        return UserDataSource(context = context)
    }

    @Provides
    fun provideErrorDataSource(stringProvider: StringProvider): ErrorDataSource {
        return ErrorDataSource(stringProvider = stringProvider)
    }

    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "publication_db"
        ).build()
    }

}