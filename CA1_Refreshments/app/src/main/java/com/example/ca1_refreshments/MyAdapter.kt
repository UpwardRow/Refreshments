package com.example.ca1_refreshments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_cells.view.*

class MyAdapter(val context: Context, val userList: SpoonacularData): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    //Defining variables with Spoonacular data
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var link: TextView
        var title: TextView

        init {
            link = itemView.link
            title = itemView.recipe_title
        }
    }

    //For recycler view to populate new rows
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_cells, parent, false))
    }

    //Used to display Recyclerview data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.link.text = userList.results[position].id.toString()
        holder.title.text = userList.results[position].title
    }

    //Returns number of items in this adapter
    override fun getItemCount(): Int {
        return userList.results.size
    }
}