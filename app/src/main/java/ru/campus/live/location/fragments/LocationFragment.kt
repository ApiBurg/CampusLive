package ru.campus.live.location.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.campus.live.R
import ru.campus.live.core.di.component.DaggerLocationComponent
import ru.campus.live.core.di.component.LocationComponent
import ru.campus.live.core.di.deps.AppDepsProvider
import ru.campus.live.core.presentation.ui.BaseFragment
import ru.campus.live.core.presentation.ui.MyOnClick
import ru.campus.live.databinding.FragmentLocationBinding
import ru.campus.live.location.adapter.LocationAdapter
import ru.campus.live.location.data.model.LocationModel
import ru.campus.live.location.viewmodel.LocationViewModel


class LocationFragment : BaseFragment<FragmentLocationBinding>() {

    private val component: LocationComponent by lazy {
        DaggerLocationComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewModel by viewModels<LocationViewModel> { component.viewModelsFactory() }
    private val myOnClick = object : MyOnClick<LocationModel> {
        override fun item(view: View, item: LocationModel) {
            binding.progressBar.isVisible = true
            viewModel.locationSave(item)
        }
    }

    private val adapter = LocationAdapter(myOnClick)
    override fun getViewBinding() = FragmentLocationBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        liveDataObserve()
        startFeedViewEvent()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.editText.doAfterTextChanged {
            binding.progressBar.isVisible = true
            viewModel.search(it.toString())
        }
    }

    private fun liveDataObserve() {
        viewModel.search()
        viewModel.liveData().observe(viewLifecycleOwner) { newModel ->
            binding.progressBar.isVisible = false
            adapter.setData(newModel)
        }
    }

    private fun startFeedViewEvent() {
        viewModel.onSuccess().observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            findNavController()
                .navigate(R.id.action_locationFragment_to_feedFragment)
        }
    }

}