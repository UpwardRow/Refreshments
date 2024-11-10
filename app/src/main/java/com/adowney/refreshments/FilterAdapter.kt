package com.adowney.refreshments

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.adowney.refreshments.utilities.LightAndDarkModeUtils
import com.google.android.material.imageview.ShapeableImageView

class FilterAdapter(private val filterList: ArrayList<FilterData>,
                    private val deleteFilterListener: OnFilterDeleteActionListener,
                    private val activityContext: Activity
    ) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    interface OnFilterDeleteActionListener{
        fun onDeleteFilterFromView(filter: String)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var foodFilter: TextView = itemView.findViewById(R.id.food_filter)
        private var deleteButton: ShapeableImageView = itemView.findViewById(R.id.delete_button)

        fun bind(index: Int) {
            deleteButton.setOnClickListener {
                removeFilter(index)

                deleteFilterListener.onDeleteFilterFromView(foodFilter.text.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val filterCellsLayoutInflater =
            LayoutInflater.from(parent.context).inflate(
                R.layout.filter_cells,
                parent,
                false
            )
        return ViewHolder(filterCellsLayoutInflater)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.foodFilter.text = filterList[position].filterName
        holder.bind(position)

        if (LightAndDarkModeUtils.isDarkMode(activityContext)){
            holder.foodFilter.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.primary))
        } else {
            holder.foodFilter.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.primary_dark))
        }
    }

    fun removeFilter(index: Int) {
        val previousListSize = itemCount
        filterList.removeAt(index)

        // These notifications are crucial for the app. The adapter will fail otherwise
        notifyItemRangeRemoved(0, previousListSize)
        notifyItemRangeInserted(0, previousListSize - 1)
        notifyItemRemoved(index)
    }
}