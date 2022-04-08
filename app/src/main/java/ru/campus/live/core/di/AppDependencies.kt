package ru.campus.live.core.di

import android.content.Context
import ru.campus.live.core.data.APIService
import ru.campus.live.feed.db.AppDatabase

interface AppDependencies {
    val context: Context
    val apiService: APIService
    val appDatabase: AppDatabase
}