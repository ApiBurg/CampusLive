package ru.campus.live.start.adapter.holder

import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.live.databinding.RowOnboardBinding
import ru.campus.live.start.data.model.StartDataObject

class StartViewHolder(
    @NonNull private val itemBinding: RowOnboardBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    private val context = itemBinding.root.context

    fun bind(model: StartDataObject) {
        itemBinding.title.text = model.title
        itemBinding.message.text = model.message
        Glide.with(context).load(model.icon).into(itemBinding.icon)
    }

}