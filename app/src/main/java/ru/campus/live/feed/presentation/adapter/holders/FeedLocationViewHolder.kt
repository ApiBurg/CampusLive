package ru.campus.live.feed.presentation.adapter.holders

import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.databinding.ItemFeedLocationBinding
import ru.campus.live.feed.data.model.FeedModel

class FeedLocationViewHolder(
    private val itemBinding: ItemFeedLocationBinding
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(model: FeedModel) {
        itemBinding.name.text = model.location!!.name
    }

}