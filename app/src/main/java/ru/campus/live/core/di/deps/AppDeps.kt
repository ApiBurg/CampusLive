package ru.campus.live.core.di.deps

import android.content.Context
import ru.campus.live.core.di.IDispatchers
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.datasource.IUserDataSource
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.feed.data.db.AppDatabase

interface AppDeps {
    var apiService: APIService
    var appDatabase: AppDatabase
    var context: Context
    var userDatabase: UserDataSource
    val userDataSource: IUserDataSource
    val IDispatchers: IDispatchers
}