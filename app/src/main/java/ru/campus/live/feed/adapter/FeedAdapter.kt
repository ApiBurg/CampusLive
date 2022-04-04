package ru.campus.live.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.core.ui.MyOnClick
import ru.campus.live.databinding.ItemFeedInviteBinding
import ru.campus.live.databinding.ItemFeedLocationBinding
import ru.campus.live.databinding.ItemPublicationBinding
import ru.campus.live.feed.adapter.diff.FeedDiffUtilCallBack
import ru.campus.live.feed.adapter.holders.FeedInviteViewHolder
import ru.campus.live.feed.adapter.holders.FeedLocationViewHolder
import ru.campus.live.feed.adapter.holders.FeedPublicationViewHolder
import ru.campus.live.feed.data.model.FeedObject
import ru.campus.live.feed.data.model.FeedViewType

class FeedAdapter(private val myOnClick: MyOnClick<FeedObject>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val model = ArrayList<FeedObject>()

    override fun getItemViewType(position: Int): Int {
        return model[position].viewType.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (FeedViewType.values()[viewType]) {
            FeedViewType.HEADING -> {
                val itemBinding =
                    ItemFeedLocationBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                FeedLocationViewHolder(itemBinding)
            }
            FeedViewType.PUBLICATION -> {
                val itemBinding =
                    ItemPublicationBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                FeedPublicationViewHolder(itemBinding, myOnClick)
            }
            else -> {
                val itemBinding = ItemFeedInviteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                FeedInviteViewHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (model[position].viewType) {
            FeedViewType.HEADING -> (holder as FeedLocationViewHolder).bind(model[position])
            FeedViewType.PUBLICATION -> (holder as FeedPublicationViewHolder).bind(model[position])
            else -> (holder as FeedInviteViewHolder).bind()
        }
    }

    override fun getItemCount(): Int {
        return model.size
    }

    fun setData(newModel: ArrayList<FeedObject>) {
        val result =
            DiffUtil.calculateDiff(FeedDiffUtilCallBack(model, newModel))
        model.clear()
        model.addAll(newModel)
        result.dispatchUpdatesTo(this)
    }

}