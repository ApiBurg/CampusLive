package ru.campus.live.location.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.campus.live.R
import ru.campus.live.core.di.AppDepsProvider
import ru.campus.live.core.di.component.DaggerLocationComponent
import ru.campus.live.core.di.component.LocationComponent
import ru.campus.live.core.ui.BaseFragment
import ru.campus.live.core.ui.MyOnClick
import ru.campus.live.databinding.FragmentLocationBinding
import ru.campus.live.location.adapter.LocationAdapter
import ru.campus.live.location.data.model.LocationDataObject
import ru.campus.live.location.viewmodel.LocationViewModel


class LocationFragment : BaseFragment<FragmentLocationBinding>() {

    private val locationComponent: LocationComponent by lazy {
        DaggerLocationComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val adapter by lazy { LocationAdapter(myOnClick) }
    private val viewModel by viewModels<LocationViewModel> {
        locationComponent.viewModelsFactory()
    }

    private val myOnClick = object : MyOnClick<LocationDataObject> {
        override fun item(view: View, item: LocationDataObject) {
            binding.progressBar.isVisible = true
            viewModel.locationSave(item)
        }
    }


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
        viewModel.feedStartEvent().observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            findNavController()
                .navigate(R.id.action_locationFragment_to_feedFragment)
        }
    }

}