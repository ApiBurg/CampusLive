package ru.campus.live.core.di.deps

import android.content.Context
import ru.campus.live.core.data.APIService
import ru.campus.live.feed.db.AppDatabase

interface AppDeps {
    var apiService: APIService
    var appDatabase: AppDatabase
    var context: Context
}