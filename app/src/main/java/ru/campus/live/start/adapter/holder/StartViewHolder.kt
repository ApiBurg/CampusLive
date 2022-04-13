package ru.campus.live.start.adapter.holder

import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.live.databinding.RowOnboardBinding
import ru.campus.live.start.data.model.StartDataObject

class StartViewHolder(
    @NonNull private val itemBinding: RowOnboardBinding,
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: StartDataObject) {
        itemBinding.title.text = item.title
        itemBinding.message.text = item.message
        val context = itemBinding.root.context
        Glide.with(context).load(item.icon).into(itemBinding.icon)
    }

}