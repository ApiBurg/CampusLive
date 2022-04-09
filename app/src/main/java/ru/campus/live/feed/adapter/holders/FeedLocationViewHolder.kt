package ru.campus.live.feed.adapter.holders

import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.core.ui.MyOnClick
import ru.campus.live.databinding.ItemFeedLocationBinding
import ru.campus.live.feed.data.model.FeedObject

class FeedLocationViewHolder(
    private val itemBinding: ItemFeedLocationBinding,
    private val myOnClick: MyOnClick<FeedObject>
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(model: FeedObject) {
        itemBinding.name.text = model.location!!.name
        itemBinding.fab.setOnClickListener {
            myOnClick.item(itemBinding.fab, model)
        }
    }

}