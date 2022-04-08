package ru.campus.live.gallery.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import ru.campus.live.R
import ru.campus.live.core.app.App
import ru.campus.live.core.di.AppDepsProvider
import ru.campus.live.core.di.component.DaggerGalleryComponent
import ru.campus.live.core.di.component.GalleryComponent
import ru.campus.live.core.ui.BaseBottomSheetDialogFragment
import ru.campus.live.core.ui.MyOnClick
import ru.campus.live.databinding.FragmentGalleryBottomSheetBinding
import ru.campus.live.gallery.adapter.GalleryAdapter
import ru.campus.live.gallery.data.model.GalleryDataObject
import ru.campus.live.gallery.viewmodel.GalleryViewModel
import ru.campus.live.gallery.views.ItemOffsetDecorationGallery

class GalleryBottomSheetDialog :
    BaseBottomSheetDialogFragment<FragmentGalleryBottomSheetBinding>() {

    private lateinit var galleryComponent: GalleryComponent
    private val viewModel by viewModels<GalleryViewModel> { galleryComponent.viewModelsFactory() }

    private val myOnClick = object : MyOnClick<GalleryDataObject> {
        override fun item(view: View, item: GalleryDataObject) {
            val bundle = Bundle()
            bundle.putParcelable("item", item)
            setFragmentResult("mediaRequest", bundle)
            dismiss()
        }
    }

    private val adapter = GalleryAdapter(myOnClick)
    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                binding.permissionsRoot.isVisible = false
                binding.recyclerView.isVisible = true
                viewModel.execute()
            } else {
                binding.permissionsRoot.isVisible = false
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        galleryComponent = DaggerGalleryComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    override fun getViewBinding() = FragmentGalleryBottomSheetBinding.inflate(layoutInflater)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permission()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        binding.recyclerView.addItemDecoration(ItemOffsetDecorationGallery(spacingInPixels))
        liveDataObserve()
    }

    private fun liveDataObserve() {
        viewModel.liveData().observe(viewLifecycleOwner) { newModel ->
            adapter.setData(newModel)
        }
    }

    private fun permission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                visiblePermissionView()
            } else {
                viewModel.execute()
            }
        } else {
            viewModel.execute()
        }
    }

    private fun visiblePermissionView() {
        binding.recyclerView.isVisible = false
        binding.permissionsRoot.isVisible = true
        binding.permissionsButton.setOnClickListener {
            permissionResult.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

}