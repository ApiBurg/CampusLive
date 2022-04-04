package ru.campus.live.feed.adapter.holders

import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.feed.data.model.FeedObject
import ru.campus.live.databinding.ItemFeedLocationBinding

class FeedLocationViewHolder(private val itemBinding: ItemFeedLocationBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(model: FeedObject) {
        itemBinding.name.text = model.location!!.name
    }

}