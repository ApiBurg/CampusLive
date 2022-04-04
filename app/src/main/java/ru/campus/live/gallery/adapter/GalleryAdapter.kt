package ru.campus.live.gallery.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.gallery.data.model.GalleryDataObject
import ru.campus.live.databinding.ItemGalleryBinding
import ru.campus.live.core.ui.MyOnClick
import ru.campus.live.gallery.adapter.holder.GalleryViewHolder

class GalleryAdapter(private val myOnClick: MyOnClick<GalleryDataObject>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val model = ArrayList<GalleryDataObject>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(itemBinding, myOnClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GalleryViewHolder).bind(model[position])
    }

    override fun getItemCount(): Int {
        return model.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newDataObject: ArrayList<GalleryDataObject>) {
        model.clear()
        model.addAll(newDataObject)
        notifyDataSetChanged()
    }

}