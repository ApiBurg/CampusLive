package ru.campus.live.start.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.databinding.RowOnboardBinding
import ru.campus.live.start.adapter.diff.StartDiffUtilCallBack
import ru.campus.live.start.adapter.holder.StartViewHolder
import ru.campus.live.start.data.model.StartDataObject

class StartAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val model = ArrayList<StartDataObject>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            RowOnboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StartViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as StartViewHolder).bind(model[position])
    }

    override fun getItemCount(): Int {
        return model.size
    }

    fun setData(newModel: ArrayList<StartDataObject>) {
        val result =
            DiffUtil.calculateDiff(StartDiffUtilCallBack(model, newModel))
        model.clear()
        model.addAll(newModel)
        result.dispatchUpdatesTo(this)
    }

}