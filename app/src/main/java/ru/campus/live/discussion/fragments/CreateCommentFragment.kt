package ru.campus.live.discussion.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.campus.live.R
import ru.campus.live.core.app.App
import ru.campus.live.core.di.component.DaggerDiscussionComponent
import ru.campus.live.core.di.component.DiscussionComponent
import ru.campus.live.core.ui.BaseFragment
import ru.campus.live.core.ui.MyOnClick
import ru.campus.live.databinding.FragmentCreateCommentBinding
import ru.campus.live.dialog.ErrorDialog
import ru.campus.live.discussion.data.model.CommentCreateObject
import ru.campus.live.discussion.viewmodel.DiscussionCreateViewModel
import ru.campus.live.gallery.adapter.UploadMediaAdapter
import ru.campus.live.gallery.data.model.GalleryDataObject
import ru.campus.live.gallery.data.model.UploadMediaObject
import ru.campus.live.gallery.fragments.GalleryBottomSheetDialog


class CreateCommentFragment : BaseFragment<FragmentCreateCommentBinding>() {

    private lateinit var discussionComponent: DiscussionComponent
    private var publication: Int = 0
    private var parent: Int = 0
    private var answered: Int = 0
    private val viewModel by viewModels<DiscussionCreateViewModel> {
        discussionComponent.viewModelsFactory()
    }

    private val uploadOnClick = object : MyOnClick<UploadMediaObject> {
        override fun item(view: View, item: UploadMediaObject) {
            viewModel.clearMediaUpload()
        }
    }

    private val uploadMediaAdapter = UploadMediaAdapter(uploadOnClick)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        discussionComponent = DaggerDiscussionComponent.builder()
            .context((activity?.applicationContext as App).appComponent.context())
            .apiService((activity?.applicationContext as App).appComponent.apiService())
            .build()
    }

    override fun getViewBinding() = FragmentCreateCommentBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            publication = it.getInt("publication")
            parent = it.getInt("parent")
            answered = it.getInt("answered")
        }
        parentFragment?.setFragmentResultListener("mediaRequest") { _, bundle ->
            val params: GalleryDataObject? = bundle.getParcelable("item")
            if (params != null) {
                binding.toolBar.menu.clear()
                viewModel.upload(params)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        binding.gallery.setOnClickListener {
            val galleryView = GalleryBottomSheetDialog()
            galleryView.show(requireActivity().supportFragmentManager, "GalleryView")
        }
        binding.recyclerViewUploadMedia.adapter = uploadMediaAdapter
        binding.recyclerViewUploadMedia.layoutManager = LinearLayoutManager(context)
        onUploadLiveData()
        onSuccessEvent()
        onFailureEvent()
    }

    private fun initToolbar() {
        binding.toolBar.title = if (parent == 0)
            getString(R.string.new_comment)
        else
            getString(R.string.reply)
        binding.toolBar.inflateMenu(R.menu.send_menu)
        binding.toolBar.setNavigationIcon(R.drawable.baseline_close_black_24)
        binding.toolBar.setNavigationOnClickListener { findNavController().popBackStack() }
        binding.toolBar.setOnMenuItemClickListener {
            binding.toolBar.menu.clear()
            binding.progressBar.isVisible = true
            sendDataOnServer()
            return@setOnMenuItemClickListener false
        }
    }

    private fun sendDataOnServer() {
        val message = binding.editText.text.toString()
        if (message.isEmpty()) return
        val params = CommentCreateObject(
            icon = 0, message = message, attachment = 0,
            parent = parent, answered = answered, publication = publication
        )
        viewModel.post(params)
    }

    private fun onUploadLiveData() {
        viewModel.onUploadLiveData().observe(viewLifecycleOwner) { model ->
            if (model.size != 0) if (!model[0].upload) binding.toolBar.inflateMenu(R.menu.send_menu)
            uploadMediaAdapter.setData(model)
        }
    }

    private fun onSuccessEvent() {
        viewModel.onSuccessEvent().observe(viewLifecycleOwner) { item ->
            val bundle = Bundle()
            bundle.putParcelable("object", item)
            setFragmentResult("discussionObject", bundle)
            findNavController().popBackStack()
        }
    }

    private fun onFailureEvent() {
        viewModel.onFailureEvent().observe(viewLifecycleOwner) { errorObject ->
            binding.progressBar.isVisible = false
            binding.toolBar.inflateMenu(R.menu.send_menu)
            val customDialog = ErrorDialog()
            val bundle = Bundle()
            bundle.putString("message", errorObject.message)
            bundle.putInt("icon", errorObject.icon)
            bundle.putInt("code", errorObject.code)
            customDialog.arguments = bundle
            customDialog.show(requireActivity().supportFragmentManager, "ErrorDialog")
        }
    }

}