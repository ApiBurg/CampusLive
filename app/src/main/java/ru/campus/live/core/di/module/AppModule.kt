package ru.campus.live.core.di.module

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.campus.live.core.di.IDispatchers
import ru.campus.live.core.di.Dispatchers
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.datasource.HostDataSource
import ru.campus.live.core.data.datasource.IUserDataSource
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.ribbon.data.db.AppDatabase

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
    fun provideDispatchersImpl(): Dispatchers {
        return Dispatchers()
    }
}

@Module
interface AppBindModule {

    @Binds
    fun bindUserDataSource(userDataSource: UserDataSource): IUserDataSource

    @Binds
    fun bindDispatchers(dispatchers: Dispatchers): IDispatchers

}