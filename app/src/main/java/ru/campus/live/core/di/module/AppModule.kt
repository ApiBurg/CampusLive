package ru.campus.live.core.di.module

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.campus.live.core.Dispatchers
import ru.campus.live.core.DispatchersImpl
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.datasource.HostDataSource
import ru.campus.live.core.data.datasource.IUserDataSource
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.feed.db.AppDatabase

@Module(includes = [AppBindModule::class])
class AppModule {

    @Provides
    fun provideAPIService(hostDataSource: HostDataSource): APIService {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(hostDataSource.domain())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(APIService::class.java)
    }

    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "publication_db"
        ).build()
    }

    @Provides
    fun provideDispatchersImpl(): DispatchersImpl {
        return DispatchersImpl()
    }

}

@Module
interface AppBindModule {

    @Binds
    fun bindUserDataSource(userDataSource: UserDataSource): IUserDataSource

    @Binds
    fun bindDispatchers(dispatchersImpl: DispatchersImpl): Dispatchers

}