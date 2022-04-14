package ru.campus.live.location.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.location.data.model.LocationModel
import ru.campus.live.databinding.ItemLocationBinding
import ru.campus.live.core.presentation.ui.MyOnClick

class LocationViewHolder(
    private val itemBinding: ItemLocationBinding,
    private val myOnClick: MyOnClick<LocationModel>
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(model: LocationModel) {
        itemBinding.name.text = model.name
        itemBinding.address.text = model.address
        itemBinding.container.setOnClickListener {
            myOnClick.item(itemBinding.container, model)
        }
    }

}