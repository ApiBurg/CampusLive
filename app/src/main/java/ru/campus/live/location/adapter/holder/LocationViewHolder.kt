package ru.campus.live.location.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.location.data.model.LocationDataObject
import ru.campus.live.databinding.ItemLocationBinding
import ru.campus.live.core.ui.MyOnClick

class LocationViewHolder(
    private val itemBinding: ItemLocationBinding,
    private val myOnClick: MyOnClick<LocationDataObject>
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(dataObject: LocationDataObject) {
        itemBinding.name.text = dataObject.name
        itemBinding.address.text = dataObject.address
        itemBinding.container.setOnClickListener {
            myOnClick.item(itemBinding.container, dataObject)
        }
    }

}