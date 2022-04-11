package ru.campus.live.start.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import ru.campus.live.R
import ru.campus.live.core.di.deps.AppDepsProvider
import ru.campus.live.core.di.component.DaggerStartComponent
import ru.campus.live.core.di.component.StartComponent
import ru.campus.live.core.ui.BaseFragment
import ru.campus.live.databinding.FragmentStartBinding
import ru.campus.live.dialog.ErrorDialog
import ru.campus.live.start.adapter.StartAdapter
import ru.campus.live.start.viewmodel.StartViewModel


class StartFragment : BaseFragment<FragmentStartBinding>() {

    private val startComponent: StartComponent by lazy {
       DaggerStartComponent.builder()
           .deps(AppDepsProvider.deps)
           .build()
    }

    private val viewModel by viewModels<StartViewModel> { startComponent.viewModelsFactory() }
    private val adapter = StartAdapter()

    override fun getViewBinding() = FragmentStartBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        liveDataObserve()
        onLoginEvent()
        onErrorEvent()
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()
        binding.start.setOnClickListener {
            isVisibleProgressBar(true)
            viewModel.login()
        }
    }

    private fun liveDataObserve() {
        viewModel.start()
        viewModel.liveData().observe(viewLifecycleOwner) { newModel ->
            adapter.setData(newModel)
        }
    }

    private fun onLoginEvent() {
        viewModel.successEvent().observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_onBoarFragment_to_locationFragment)
        }
    }

    private fun onErrorEvent() {
        viewModel.failureEvent().observe(viewLifecycleOwner) { errorObject ->
            isVisibleProgressBar(false)
            val bundle = Bundle()
            bundle.putParcelable("params", errorObject)
            val customDialog = ErrorDialog()
            customDialog.arguments = bundle
            customDialog.show(requireActivity().supportFragmentManager, "CustomDialogError")
        }
    }

    private fun isVisibleProgressBar(visibility: Boolean) {
        binding.progressBar.isVisible = visibility
        binding.start.isVisible = !visibility
    }

}