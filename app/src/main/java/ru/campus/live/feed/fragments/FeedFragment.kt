package ru.campus.live.feed.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.campus.live.R
import ru.campus.live.core.app.App
import ru.campus.live.core.di.component.DaggerFeedComponent
import ru.campus.live.core.di.component.FeedComponent
import ru.campus.live.core.ui.BaseFragment
import ru.campus.live.core.ui.MyOnClick
import ru.campus.live.databinding.FragmentFeedBinding
import ru.campus.live.feed.adapter.FeedAdapter
import ru.campus.live.feed.data.model.FeedObject
import ru.campus.live.feed.viewmodel.FeedViewModel
import java.util.concurrent.atomic.AtomicBoolean


class FeedFragment : BaseFragment<FragmentFeedBinding>() {

    private lateinit var feedComponent: FeedComponent
    private val adapter by lazy { FeedAdapter(myOnClick) }
    private val viewModel: FeedViewModel by navGraphViewModels(R.id.feedFragment) {
        feedComponent.viewModelsFactory()
    }

    private val myOnClick = object : MyOnClick<FeedObject> {
        override fun item(view: View, item: FeedObject) {
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        feedComponent = DaggerFeedComponent.builder()
            .context((activity?.applicationContext as App).appComponent.context())
            .apiService((activity?.applicationContext as App).appComponent.apiService())
            .build()
    }

    override fun getViewBinding() = FragmentFeedBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragment?.setFragmentResultListener("new_publication") { _, bundle ->
            Log.d("MyLog", "Полученна новая публикация!")
            val params: FeedObject? = bundle.getParcelable("publication")
            if (params != null) viewModel.insert(params)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        liveDataObserve()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_createPublicationFragment)
        }
        onCommentEvent()
        onComplaintEvent()
    }

    private fun liveDataObserve() {
        viewModel.liveData().observe(viewLifecycleOwner) { newModel ->
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
                        binding.fab.show()
                        viewModel.complaintSendDataOnServer(it)
                    }
                }

                override fun onShown(snackbar: Snackbar) {
                    binding.fab.hide()
                }
            })
            snack.show()
        }
    }

}