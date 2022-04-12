package ru.campus.live.discussion.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.campus.live.R
import ru.campus.live.core.di.component.DaggerDiscussionComponent
import ru.campus.live.core.di.component.DiscussionComponent
import ru.campus.live.core.di.deps.AppDepsProvider
import ru.campus.live.core.ui.BaseFragment
import ru.campus.live.core.ui.BounceEdgeEffectFactory
import ru.campus.live.core.ui.MyOnClick
import ru.campus.live.databinding.FragmentDiscussionBinding
import ru.campus.live.discussion.adapter.DiscussionAdapter
import ru.campus.live.discussion.data.model.DiscussionObject
import ru.campus.live.discussion.viewmodel.DiscussionViewModel
import ru.campus.live.feed.data.model.FeedObject
import java.util.concurrent.atomic.AtomicBoolean


class DiscussionFragment : BaseFragment<FragmentDiscussionBinding>() {

    private var publicationObject: FeedObject? = null
    private val discussionComponent: DiscussionComponent by lazy {
        DaggerDiscussionComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewModel: DiscussionViewModel by navGraphViewModels(R.id.discussionFragment) {
        discussionComponent.viewModelsFactory()
    }

    private val myOnClick = object : MyOnClick<DiscussionObject> {
        override fun item(view: View, item: DiscussionObject) {
            if (item.hidden == 0) {
                val bottomSheetDialog = DiscussionBottomSheetFragment()
                val bundle = Bundle()
                bundle.putParcelable("item", item)
                bottomSheetDialog.arguments = bundle
                bottomSheetDialog.show(
                    (requireActivity().supportFragmentManager),
                    "BottomSheetDialog"
                )
            }
        }
    }

    private val adapter = DiscussionAdapter(myOnClick)
    override fun getViewBinding() = FragmentDiscussionBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { publicationObject = it.getParcelable("publication") }
        parentFragment?.setFragmentResultListener("discussionObject") { _, bundle ->
            Log.d("MyLog", "Получен эвент с новым комментарием!")
            val params: DiscussionObject? = bundle.getParcelable("object")
            if (params != null) viewModel.insert(params)
        }
        parentFragment?.setFragmentResultListener("reply") { _, bundle ->
            val params: DiscussionObject? = bundle.getParcelable("object")
            if (params != null) onReplyEvent(params)
        }
        viewModel.get(publicationObject)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.edgeEffectFactory = BounceEdgeEffectFactory()
        initToolBar()
        liveDataObserve()
        onEventTitle()
        onComplaintEvent()
        onScrollEvent()
    }

    private fun initToolBar() {
        isProgressBarVisible(false)
        binding.toolbar.setNavigationIcon(R.drawable.baseline_close_black_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.setOnMenuItemClickListener {
            isProgressBarVisible(true)
            viewModel.refresh()
            return@setOnMenuItemClickListener false
        }
        binding.fab.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("publication", publicationObject?.id ?: 0)
            findNavController().navigate(
                R.id.action_discussionFragment_to_createCommentFragment,
                bundle
            )
        }
    }

    private fun liveDataObserve() {
        viewModel.liveData().observe(viewLifecycleOwner) { model ->
            isProgressBarVisible(false)
            adapter.setData(model)
        }
    }

    private fun onEventTitle() {
        viewModel.getTitleLiveData().observe(viewLifecycleOwner) { title ->
            binding.toolbar.title = title
        }
    }

    private fun onComplaintEvent() {
        viewModel.complaintEvent().observe(viewLifecycleOwner) {
            val isCancel = AtomicBoolean(false)
            val snack = Snackbar.make(
                binding.root, getString(R.string.complaint_response),
                Snackbar.LENGTH_LONG
            )
            val snackView = snack.view
            val tv =
                snackView.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
            tv.setTextColor("#f44336".toColorInt())
            snack.setAction(R.string.close) { isCancel.set(true) }
            snack.addCallback(object : Snackbar.Callback() {
                override fun onDismissed(snackbar: Snackbar, event: Int) {
                    if (!isCancel.get()) {
                        binding.fab.show()
                    }
                }

                override fun onShown(snackbar: Snackbar) {
                    binding.fab.hide()
                }
            })
            snack.show()
        }
    }

    private fun onReplyEvent(item: DiscussionObject) {
        val parent = if (item.parent == 0) item.id else item.parent
        val bundle = Bundle()
        bundle.putInt("publication", publicationObject!!.id)
        bundle.putInt("parent", parent)
        bundle.putInt("answered", item.id)
        findNavController().navigate(
            R.id.action_discussionFragment_to_createCommentFragment,
            bundle
        )
    }

    private fun isProgressBarVisible(visible: Boolean) {
        if (visible) {
            binding.progressBar.isVisible = true
            binding.toolbar.menu.clear()
        } else {
            binding.progressBar.isVisible = false
            if (!binding.toolbar.menu.hasVisibleItems())
                binding.toolbar.inflateMenu(R.menu.discussion_menu)
        }
    }

    private fun onScrollEvent() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy < 0 && !binding.fab.isShown) {
                    binding.fab.show()
                } else if (dy > 0 && binding.fab.isShown) {
                    binding.fab.hide()
                }
            }
        })
    }

}