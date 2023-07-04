package com.adowney.refreshments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adowney.refreshments.databinding.ActivityResultsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Start of the Spoonacular URL
const val SPOONACULAR_BASE_URL = "https://api.spoonacular.com/"

class ResultsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultsBinding
    lateinit var myAdapter: MyAdapter
    lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // This will tell the view to not change its size if it conflicts with other layouts
        binding.recyclerviewRecipes.setHasFixedSize(true)


        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerviewRecipes.layoutManager = linearLayoutManager
//
//        getSpoonacularData()
        newGetMySpoonacularData()
        supportActionBar?.hide()
    }

    private fun newGetMySpoonacularData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SPOONACULAR_BASE_URL)
            .build()
            .create(APIInterface::class.java)

        val userIngredientsList = ArrayList<String>()
        userIngredientsList.add("Coconut Milk")

        val retrofitData = retrofitBuilder.getSpoonacularData(
            userIngredientsList,
            "Curry"
        )

        retrofitData.enqueue(object : Callback<SpoonacularData?> {
            override fun onResponse(
                call: Call<SpoonacularData?>,
                response: Response<SpoonacularData?>
            ) {
                val responseBodySpoonacularData = response.body()!!

                myAdapter = MyAdapter(baseContext, responseBodySpoonacularData)
                myAdapter.notifyDataSetChanged()
                binding.recyclerviewRecipes.adapter = myAdapter
                }

            override fun onFailure(call: Call<SpoonacularData?>, t: Throwable) {
                Log.d("MainActivity", "OnFailure" + t.message)
            }
        })
    }
}