package com.adowney.refreshments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class SearchAdapter(val context: Context, val recipeList: Result):
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    companion object {
        // This function may be used later to access the filters of results entered for account
        /*fun getFilter(): Any {

        }*/
    }

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        var recipeTitle: TextView = itemView.findViewById(R.id.recipe_title)
        var link: TextView = itemView.findViewById(R.id.link)

        init{
            itemView.setOnClickListener{ v: View ->
                val position: Int = adapterPosition
                Toast.makeText(
                    itemView.context,
                    "You clicked on item # ${position + 1}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val recipeCellsLayoutInflater =
            LayoutInflater.from(context).inflate(R.layout.recipe_cells,
            parent,
            false
        )
        return ViewHolder(recipeCellsLayoutInflater)
    }

    override fun getItemCount(): Int {
        return recipeList.hits.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.link.movementMethod
        holder.recipeTitle.text = recipeList.hits[position].recipe.recipeTitle
        holder.link.text = recipeList.hits[position].recipe.url
        val linkItem = recipeList.hits[position]

       /* fun linkTap(item: HitsData){*/
            //link.text = item.recipe.url
        holder.itemView.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkItem.recipe.url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}