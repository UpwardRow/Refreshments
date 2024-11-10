package com.adowney.refreshments

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.adowney.refreshments.utilities.LightAndDarkModeUtils

class QuickFilterAdapter(
    private val foundQuickFilters: List<String>,
    private val checkedQuickFilterListener: OnQuickFilterCheckedActionListener,
    private val activityContext: Activity
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

        if (LightAndDarkModeUtils.isDarkMode(activityContext)){
            holder.quickFilterText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.primary))
        } else {
            holder.quickFilterText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.primary_dark))
        }

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