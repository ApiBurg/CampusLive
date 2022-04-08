package ru.campus.live.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.campus.live.R
import ru.campus.live.core.di.AppDepsProvider
import ru.campus.live.core.di.component.DaggerMainComponent
import ru.campus.live.core.di.component.MainComponent

class MainActivity : AppCompatActivity() {

    lateinit var mainComponent: MainComponent
    private var navController: NavController? = null
    private val viewModel by viewModels<MainViewModel> {
        mainComponent.viewModelsFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainComponent = DaggerMainComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainNavigationHost) as NavHostFragment
        navController = navHostFragment.navController
        liveDataObserve()
    }

    private fun liveDataObserve() {
        viewModel.authEvent().observe(this) {
            navController?.navigate(R.id.action_onBoarFragment_to_feedFragment)
        }
    }

}