package ru.campus.live.feed.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.campus.live.R
import ru.campus.live.core.di.deps.AppDepsProvider
import ru.campus.live.core.di.component.DaggerFeedComponent
import ru.campus.live.core.di.component.FeedComponent
import ru.campus.live.core.ui.BaseFragment
import ru.campus.live.core.ui.MyOnClick
import ru.campus.live.databinding.FragmentCreatePublicationBinding
import ru.campus.live.dialog.ErrorDialog
import ru.campus.live.feed.data.model.PublicationPostObject
import ru.campus.live.feed.viewmodel.CreatePublicationViewModel
import ru.campus.live.gallery.adapter.UploadMediaAdapter
import ru.campus.live.gallery.data.model.GalleryDataObject
import ru.campus.live.gallery.data.model.UploadMediaObject
import ru.campus.live.gallery.fragments.GalleryBottomSheetDialog


class CreatePublicationFragment : BaseFragment<FragmentCreatePublicationBinding>() {

    private val feedComponent: FeedComponent by lazy {
        DaggerFeedComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewModel: CreatePublicationViewModel by viewModels {
        feedComponent.viewModelsFactory()
    }

    private val deleteCallBack = object : MyOnClick<UploadMediaObject> {
        override fun item(view: View, item: UploadMediaObject) {
            viewModel.clearMediaList()
        }
    }

    private val uploadMediaAdapter = UploadMediaAdapter(deleteCallBack)
    override fun getViewBinding() = FragmentCreatePublicationBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragment?.setFragmentResultListener("mediaRequest") { _, bundle ->
            val params: GalleryDataObject? = bundle.getParcelable("item")
            if (params != null) viewModel.upload(params)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        onPostSuccess()
        onPostFailure()
        onUploadLiveData()
        binding.gallery.setOnClickListener {
            val galleryView = GalleryBottomSheetDialog()
            galleryView.show(requireActivity().supportFragmentManager, "GalleryView")
        }
        binding.recyclerViewUploadMedia.adapter = uploadMediaAdapter
        binding.recyclerViewUploadMedia.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initToolbar() {
        binding.toolBar.setTitle(R.string.new_publication)
        binding.toolBar.setNavigationIcon(R.drawable.outline_clear_black_24)
        isVisibleToolBarMenu(true)
        binding.toolBar.setNavigationOnClickListener { findNavController().popBackStack() }
        binding.toolBar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.send) {
                isVisibleToolBarMenu(false)
                binding.progressBar.isVisible = true
                val message = binding.editText.text.toString()
                if (message.isEmpty()) return@setOnMenuItemClickListener false
                val params = PublicationPostObject(message = message, attachment = 0)
                viewModel.post(params)
            }
            return@setOnMenuItemClickListener false
        }
    }

    private fun isVisibleToolBarMenu(visible: Boolean) {
        if (visible)
            binding.toolBar.inflateMenu(R.menu.send_menu)
        else
            binding.toolBar.menu.clear()
    }

    private fun onUploadLiveData() {
        viewModel.onUploadLiveData().observe(viewLifecycleOwner) { newModel ->
            uploadMediaAdapter.setData(newModel)
        }
    }

    private fun onPostSuccess() {
        viewModel.onSuccessEvent().observe(viewLifecycleOwner) { feedObject ->
            val bundle = Bundle()
            bundle.putParcelable("publication", feedObject)
            parentFragment?.setFragmentResult("new_publication", bundle)
            findNavController().popBackStack()
        }
    }

    private fun onPostFailure() {
        viewModel.onFailureEvent().observe(viewLifecycleOwner) { errorParams ->
            isVisibleToolBarMenu(false)
            val customDialog = ErrorDialog()
            val bundle = Bundle()
            bundle.putString("message", errorParams.message)
            bundle.putInt("icon", errorParams.icon)
            bundle.putInt("code", errorParams.code)
            customDialog.arguments = bundle
            customDialog.show(requireActivity().supportFragmentManager, "CustomErrorDialog")
        }
    }

}