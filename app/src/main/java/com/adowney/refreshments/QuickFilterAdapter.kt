package com.adowney.refreshments

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

class QuickFilterAdapter(
    private val foundQuickFilters: List<String>,
    private val checkedQuickFilterListener: OnQuickFilterCheckedActionListener,
) : RecyclerView.Adapter<QuickFilterAdapter.ViewHolder>() {

    private val quickFilters = PredefinedQuickFilters.getAllQuickFilters()

    interface OnQuickFilterCheckedActionListener{
        fun onCheckedQuickFilterFromView(quickFilter: String, checked: Boolean)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var quickFilter: CheckBox = itemView.findViewById(R.id.quick_filter_checkbox)
        var quickFilterText: TextView = itemView.findViewById(R.id.quick_filter_text)

        fun bind(index: Int) {
            quickFilter.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    Log.d("Checker", "Checked ")

                    checkedQuickFilterListener
                        .onCheckedQuickFilterFromView(quickFilterText.text.toString(), true)
                } else {
                    Log.d("Checker", "Unchecked")

                    checkedQuickFilterListener
                        .onCheckedQuickFilterFromView(quickFilterText.text.toString(), false)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val quickFilterCellsLayoutInflater = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.quick_filter_cells, viewGroup, false)
        return ViewHolder(quickFilterCellsLayoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.quickFilterText.text = quickFilters[position].quickFilterName

        if(foundQuickFilters.isNotEmpty()){
            holder.quickFilter.isChecked = foundQuickFilters.contains(holder.quickFilterText.text)
        } else{
            holder.quickFilter.isChecked = false
        }

        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return quickFilters.size
    }
}