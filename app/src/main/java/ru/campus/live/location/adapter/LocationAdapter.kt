package ru.campus.live.location.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.core.ui.MyOnClick
import ru.campus.live.databinding.ItemLocationBinding
import ru.campus.live.location.adapter.diff.LocationDiffUtilCallBack
import ru.campus.live.location.adapter.holder.LocationViewHolder
import ru.campus.live.location.data.model.LocationDataObject

class LocationAdapter(private val myOnClick: MyOnClick<LocationDataObject>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val model = ArrayList<LocationDataObject>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(itemBinding, myOnClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LocationViewHolder).bind(model[position])
    }

    override fun getItemCount(): Int {
        return model.size
    }

    fun setData(newModel: List<LocationDataObject>) {
        val result =
            DiffUtil.calculateDiff(LocationDiffUtilCallBack(model,
                newModel as ArrayList<LocationDataObject>))
        model.clear()
        model.addAll(newModel)
        result.dispatchUpdatesTo(this)
    }

}