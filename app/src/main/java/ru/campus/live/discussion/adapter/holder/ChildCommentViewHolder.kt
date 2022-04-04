package ru.campus.live.discussion.adapter.holder

import android.annotation.SuppressLint
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.live.R
import ru.campus.live.core.data.datasource.HostDataSource
import ru.campus.live.core.ui.MyOnClick
import ru.campus.live.databinding.ItemChildCommentBinding
import ru.campus.live.discussion.data.model.DiscussionObject
import kotlin.math.roundToInt

class ChildCommentViewHolder(
    private val itemBinding: ItemChildCommentBinding,
    private val myOnClick: MyOnClick<DiscussionObject>
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    private val context = itemBinding.root.context
    private val host = HostDataSource(context).domain()

    fun bind(model: DiscussionObject) {
        itemBinding.message.text = model.message
        itemBinding.date.text = model.relativeTime
        Glide.with(context).load(userAvatar(model)).into(itemBinding.userPhoto)
        renderMediaView(model)
        renderVoteView(model)
        renderRatingView(model)
        itemBinding.container.setOnClickListener {
            myOnClick.item(itemBinding.container, model)
        }
    }

    private fun userAvatar(model: DiscussionObject): String {
        var pathUserIcon = host + "media/icon/" + model.icon_id + ".png"
        if (model.icon_id < 10) pathUserIcon = host + "media/icon/" + model.icon_id + ".png"
        return pathUserIcon
    }

    private fun renderMediaView(model: DiscussionObject) {
        if (model.attachment == null) {
            itemBinding.photo.isVisible = false
        } else {
            itemBinding.photo.isVisible = true
            val displayMetrics = context.resources.displayMetrics
            val pxAndDp = (80 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
            val k = pxAndDp.toFloat() / model.attachment.width.toFloat()
            val height = (model.attachment.height.toFloat() * k).roundToInt()
            val params: ViewGroup.LayoutParams = itemBinding.photo.layoutParams
            params.width = pxAndDp
            params.height = height
            itemBinding.photo.layoutParams = params
            Glide.with(context)
                .load(host + model.attachment.path)
                .into(itemBinding.photo)
        }
    }

    private fun renderVoteView(model: DiscussionObject) {
        when (model.vote) {
            0 -> {
                itemBinding.likeStatus.isVisible = false
            }
            1 -> {
                itemBinding.likeStatus.isVisible = true
                itemBinding.likeStatus.setImageResource(R.drawable.like)
            }
            2 -> {
                itemBinding.likeStatus.isVisible = true
                itemBinding.likeStatus.setImageResource(R.drawable.dislike)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderRatingView(model: DiscussionObject) {
        when {
            model.rating == 0 -> {
                itemBinding.rating.isVisible = false
            }
            model.rating > 0 -> {
                itemBinding.rating.isVisible = true
                itemBinding.rating.text = "+" + model.rating
                itemBinding.rating.setTextColor("#2e703c".toColorInt())
            }
            else -> {
                itemBinding.rating.isVisible = true
                itemBinding.rating.text = model.rating.toString()
                itemBinding.rating.setTextColor("#8a0c0c".toColorInt())
            }
        }
    }

}