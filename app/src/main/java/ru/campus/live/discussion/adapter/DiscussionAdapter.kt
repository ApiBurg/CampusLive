package ru.campus.live.discussion.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.core.ui.MyOnClick
import ru.campus.live.databinding.ItemChildCommentBinding
import ru.campus.live.databinding.ItemParentCommetBinding
import ru.campus.live.databinding.ItemPublicationBinding
import ru.campus.live.discussion.adapter.diff.DiscussionDiffUtilCallBack
import ru.campus.live.discussion.adapter.holder.ChildCommentViewHolder
import ru.campus.live.discussion.adapter.holder.DiscussionPublicationViewHolder
import ru.campus.live.discussion.adapter.holder.ParentCommentViewHolder
import ru.campus.live.discussion.data.model.DiscussionObject
import ru.campus.live.feed.adapter.diff.FeedDiffUtilCallBack

class DiscussionAdapter(
    private val myOnClick: MyOnClick<DiscussionObject>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val model = ArrayList<DiscussionObject>()

    override fun getItemViewType(position: Int): Int {
        return model[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> {
                val itemBinding =
                    ItemParentCommetBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                return ParentCommentViewHolder(itemBinding, myOnClick)
            }
            2 -> {
                val itemBinding =
                    ItemChildCommentBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                return ChildCommentViewHolder(itemBinding, myOnClick)
            }
            else -> {
                val itemBinding =
                    ItemPublicationBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                return DiscussionPublicationViewHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (model[position].type) {
            1 -> (holder as ParentCommentViewHolder).bind(model[position])
            2 -> (holder as ChildCommentViewHolder).bind(model[position])
            else -> (holder as DiscussionPublicationViewHolder).bind(model[position])
        }
    }

    override fun getItemCount(): Int {
        return model.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newModel: ArrayList<DiscussionObject>) {
        val result =
            DiffUtil.calculateDiff(DiscussionDiffUtilCallBack(model, newModel))
        model.clear()
        model.addAll(newModel)
        result.dispatchUpdatesTo(this)
    }

}