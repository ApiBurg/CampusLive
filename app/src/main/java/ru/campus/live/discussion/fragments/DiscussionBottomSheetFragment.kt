package ru.campus.live.discussion.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.navigation.navGraphViewModels
import ru.campus.live.R
import ru.campus.live.core.di.AppDepsProvider
import ru.campus.live.core.di.component.DaggerDiscussionComponent
import ru.campus.live.core.di.component.DiscussionComponent
import ru.campus.live.core.ui.BaseBottomSheetDialogFragment
import ru.campus.live.databinding.FragmentDiscussionBottomSheetBinding
import ru.campus.live.discussion.data.model.DiscussionObject
import ru.campus.live.discussion.viewmodel.DiscussionViewModel

class DiscussionBottomSheetFragment :
    BaseBottomSheetDialogFragment<FragmentDiscussionBottomSheetBinding>(), View.OnClickListener {

    private var item: DiscussionObject? = null
    private val discussionComponent: DiscussionComponent by lazy {
        DaggerDiscussionComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    override fun getViewBinding() = FragmentDiscussionBottomSheetBinding.inflate(layoutInflater)
    private val viewModel: DiscussionViewModel by navGraphViewModels(R.id.discussionFragment) {
        discussionComponent.viewModelsFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { item = it.getParcelable("item") }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.commentId.text = "id" + item!!.id
        binding.complaint.setOnClickListener(this)
        binding.up.setOnClickListener(this)
        binding.down.setOnClickListener(this)
        binding.reply.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.complaint -> viewModel.complaint(item!!)
            R.id.reply -> {
                val bundle = Bundle()
                bundle.putParcelable("object", item)
                setFragmentResult("reply", bundle)
            }
            R.id.up -> {
                viewModel.vote(item = item!!, vote = 1)
            }
            R.id.down -> {
                viewModel.vote(item = item!!, vote = 2)
            }
        }
        dismiss()
    }


}