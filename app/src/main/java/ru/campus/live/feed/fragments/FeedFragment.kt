package ru.campus.live.feed.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.campus.live.R
import ru.campus.live.core.di.component.DaggerFeedComponent
import ru.campus.live.core.di.component.FeedComponent
import ru.campus.live.core.di.deps.AppDepsProvider
import ru.campus.live.core.presentation.ui.BaseFragment
import ru.campus.live.core.presentation.ui.MyOnClick
import ru.campus.live.databinding.FragmentFeedBinding
import ru.campus.live.feed.adapter.FeedAdapter
import ru.campus.live.feed.data.model.FeedModel
import ru.campus.live.feed.viewmodel.FeedViewModel
import java.util.concurrent.atomic.AtomicBoolean


class FeedFragment : BaseFragment<FragmentFeedBinding>() {

    private val feedComponent: FeedComponent by lazy {
        DaggerFeedComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val adapter by lazy { FeedAdapter(myOnClick) }
    private var linearLayoutManager: LinearLayoutManager? = null
    private val viewModel: FeedViewModel by navGraphViewModels(R.id.feedFragment) {
        feedComponent.viewModelsFactory()
    }

    private val myOnClick = object : MyOnClick<FeedModel> {
        override fun item(view: View, item: FeedModel) {
            if (view.id == R.id.fab) {
                findNavController().navigate(R.id.action_feedFragment_to_createPublicationFragment)
            } else {
                val bottomSheetDialog = FeedBottomSheetDialogFragment()
                val bundle = Bundle()
                bundle.putParcelable("publication_object", item)
                bottomSheetDialog.arguments = bundle
                bottomSheetDialog.show(
                    requireActivity().supportFragmentManager,
                    "FeedBottomSheetDialog"
                )
            }
        }
    }

    override fun getViewBinding() = FragmentFeedBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragment?.setFragmentResultListener("new_publication") { _, bundle ->
            val params: FeedModel? = bundle.getParcelable("publication")
            if (params != null) viewModel.insert(params)
        }
        viewModel.getCache()
        viewModel.get()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        liveDataObserve()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = linearLayoutManager
        onCommentEvent()
        onComplaintEvent()
        onScrollEvent()
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_createPublicationFragment)
        }

        binding.swipeRefreshLayout.setColorSchemeColors("#517fba".toColorInt())
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.get(refresh = true)
        }
    }

    private fun liveDataObserve() {
        viewModel.liveData().observe(viewLifecycleOwner) { newModel ->
            if(binding.swipeRefreshLayout.isRefreshing) binding.swipeRefreshLayout.isRefreshing = false
            adapter.setData(newModel)
        }
    }

    private fun onCommentEvent() {
        viewModel.onCommentStartViewEvent().observe(viewLifecycleOwner) { model ->
            val bundle = Bundle()
            bundle.putParcelable("publication", model)
            findNavController().navigate(R.id.action_feedFragment_to_discussionFragment, bundle)
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
                        viewModel.complaintSendDataOnServer(it)
                    }
                }

                override fun onShown(snackbar: Snackbar) {
                }
            })
            snack.show()
        }
    }

    private fun onScrollEvent() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = linearLayoutManager?.childCount ?: 0
                val totalItemCount = linearLayoutManager?.itemCount ?: 0
                val firstVisibleItem = linearLayoutManager?.findFirstVisibleItemPosition() ?: 0
                if (visibleItemCount + firstVisibleItem >= totalItemCount) viewModel.get()

                if (dy < 0 && !binding.fab.isShown) {
                    binding.fab.show()
                } else if (dy > 0 && binding.fab.isShown) {
                    binding.fab.hide()
                }
            }
        })
    }

}