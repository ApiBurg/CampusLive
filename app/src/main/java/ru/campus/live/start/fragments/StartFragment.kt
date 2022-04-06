package ru.campus.live.start.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import ru.campus.live.R
import ru.campus.live.core.app.App
import ru.campus.live.core.di.component.DaggerStartComponent
import ru.campus.live.core.di.component.StartComponent
import ru.campus.live.core.ui.BaseFragment
import ru.campus.live.databinding.FragmentStartBinding
import ru.campus.live.dialog.ErrorDialog
import ru.campus.live.start.adapter.StartAdapter
import ru.campus.live.start.viewmodel.StartViewModel


class StartFragment : BaseFragment<FragmentStartBinding>() {

    private lateinit var startComponent: StartComponent
    private val adapter = StartAdapter()
    private val viewModel by viewModels<StartViewModel> {
        startComponent.viewModelsFactory()
    }

    override fun getViewBinding() = FragmentStartBinding.inflate(layoutInflater)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        startComponent = DaggerStartComponent.builder()
            .apiService((activity?.applicationContext as App).appComponent.apiService())
            .stringProvider((activity?.applicationContext as App).appComponent.stringProvider())
            .userDataSource((activity?.applicationContext as App).appComponent.userDataSource())
            .errorDataSource((activity?.applicationContext as App).appComponent.errorDataSource())
            .build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        liveDataObserve()
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()
        onLoginEvent()
        onErrorEvent()
        binding.start.setOnClickListener {
            binding.start.isVisible = false
            binding.progressBar.isVisible = true
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
            binding.progressBar.visibility = View.GONE
            binding.start.visibility = View.VISIBLE
            val bundle = Bundle()
            bundle.putString("message", errorObject.message)
            bundle.putInt("icon", errorObject.icon)
            val customDialog = ErrorDialog()
            customDialog.arguments = bundle
            customDialog.show(requireActivity().supportFragmentManager, "CustomDialogError")
        }
    }

}