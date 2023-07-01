package com.adowney.refreshments
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.adowney.refreshments.databinding.RecyclerViewItemBinding
//import com.google.android.gms.tasks.Task
//import kotlinx.android.synthetic.main.row_cells.view.*
//
//class MyAdapter(val context: Context, val userList: SpoonacularData): RecyclerView.Adapter<MyAdapter.ViewHolder>() {
//
//
//    // For recycler view to populate new rows
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_cells, parent, false))
//    }
//
//    // Used to display Recyclerview data
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.link.text = userList.results[position].id.toString()
//        holder.title.text = userList.results[position].title
//    }
//
//    // Returns number of items in this adapter
//    override fun getItemCount(): Int {
//        return userList.results.size
//    }
//
//    // Defining variables with Spoonacular data
//    // This inner class ViewHolder has an itemBinding parameter,
//    // which is inheriting from RecyclerView.ViewHolder()
//    inner class ViewHolder (val itemBinding: RecyclerViewItemBinding)
//        :RecyclerView.ViewHolder(itemBinding.root) {
//            fun bindItem(task:Task){
//                itemBinding.textView1.text = task.
//                var link: TextView = null
//                var title: TextView? = null
//            }
//
////        var link: TextView
////        var title: TextView
//
////        init {
////            link = itemView.link
//////            title = itemView.recipe_title
////        }
//    }
//}