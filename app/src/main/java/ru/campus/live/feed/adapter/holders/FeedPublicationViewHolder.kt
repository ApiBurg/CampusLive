package ru.campus.live.feed.adapter.holders

import android.annotation.SuppressLint
import android.util.DisplayMetrics
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.live.R
import ru.campus.live.core.data.datasource.HostDataSource
import ru.campus.live.discussion.domain.usecase.CommentsDeclinationUseCase
import ru.campus.live.core.ui.MyOnClick
import ru.campus.live.databinding.ItemPublicationBinding
import ru.campus.live.feed.data.model.FeedObject
import kotlin.math.roundToInt

class FeedPublicationViewHolder(
    private val itemBinding: ItemPublicationBinding,
    private val myOnClick: MyOnClick<FeedObject>
) : RecyclerView.ViewHolder(itemBinding.root) {

    private val context = itemBinding.root.context
    private val commentsDeclinationUseCase = CommentsDeclinationUseCase(context)
    private val host = HostDataSource(context).domain()

    fun bind(model: FeedObject) {
        itemBinding.message.text = model.message
        itemBinding.date.text = model.relativeTime
        itemBinding.comment.text = commentsDeclinationUseCase.getTextComments(model.comments)
        renderMediaView(model)
        renderVoteView(model)
        renderRatingView(model)
        itemBinding.container.setOnClickListener {
            myOnClick.item(itemBinding.container, model)
        }
    }

    private fun renderMediaView(model: FeedObject) {
        if (model.attachment != null && model.attachment.id != 0) {
            itemBinding.media.isVisible = true
            val displayMetrics = context.resources.displayMetrics
            val pxAndDp = (80 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
            val k = pxAndDp.toFloat() / model.attachment.width.toFloat()
            val height = (model.attachment.height.toFloat() * k).roundToInt()
            val params: ViewGroup.LayoutParams = itemBinding.media.layoutParams
            params.width = pxAndDp
            params.height = height
            itemBinding.media.layoutParams = params
            Glide.with(context).load(host + model.attachment.path).into(itemBinding.media)
        } else {
            itemBinding.media.isVisible = false
        }
    }

    private fun renderVoteView(model: FeedObject) {
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
    private fun renderRatingView(model: FeedObject) {
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