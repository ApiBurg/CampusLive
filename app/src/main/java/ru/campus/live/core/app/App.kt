package ru.campus.live.core.app

import android.app.Application
import ru.campus.live.core.di.AppDepsStore
import ru.campus.live.core.di.component.AppComponent
import ru.campus.live.core.di.component.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(context = this)
            .build()
        AppDepsStore.deps = appComponent
    }


}