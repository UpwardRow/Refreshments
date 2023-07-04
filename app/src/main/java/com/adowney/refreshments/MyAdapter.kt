package com.adowney.refreshments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val context: Context, val recipeList: SpoonacularData): RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        var recipeTitle: TextView
        var link: TextView

        init{
            // It may be possible to use adapter binding in this block to make the code more
            // readable, but I am unsure at the moment. Until then I will access the id directly
            recipeTitle = itemView.findViewById(R.id.recipe_title)
            link  = itemView.findViewById(R.id.link)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.row_cells,
            parent,
            false
        )
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return recipeList.results.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recipeTitle.text = recipeList.results[position].recipeTitle
        holder.link.text = recipeList.results[position].recipeTitle
    }
}