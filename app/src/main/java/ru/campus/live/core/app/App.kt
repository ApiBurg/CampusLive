package ru.campus.live.core.app

import android.app.Application
import ru.campus.live.core.di.AppDepsStore
import ru.campus.live.core.di.component.AppComponent
import ru.campus.live.core.di.component.DaggerAppComponent

class App : Application() {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(context = this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        AppDepsStore.deps = appComponent
    }

}