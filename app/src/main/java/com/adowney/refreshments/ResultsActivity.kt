package com.adowney.refreshments

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adowney.refreshments.HomeActivity.Companion.USER_QUERY
import com.adowney.refreshments.databinding.ActivityResultsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ResultsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "HomeActivity"

        // Start of the Edamam URL
        private const val EDAMAM_BASE_URL = "https://api.edamam.com/"

        // This is where the API key and app is for the url, which is hidden from potential threats
        private const val APP_KEY = BuildConfig.EDAMAM_APP_KEY
        private const val APP_ID = BuildConfig.EDAMAM_APP_ID
    }

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

        getMyEdamamData()
    }

    private fun getMyEdamamData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(EDAMAM_BASE_URL)
            .build()
            .create(APIInterface::class.java)

        /* This is the list for the ingredients to be excluded. This will be removed later to be
           accessed inside the user's database data
        */
        val exclusionsList = listOf("Chicken","Onion","Rabbit")

        val retrofitData = retrofitBuilder.getEdamamData(
            APP_KEY,
            APP_ID,
            "public",
            USER_QUERY,
            exclusionsList
        )

        retrofitData.enqueue(object : Callback<Result?> {
            override fun onResponse(
                call: Call<Result?>,
                response: Response<Result?>
            ) {
                val responseBodyAPIData = response.body()!!

                myAdapter = MyAdapter(baseContext, responseBodyAPIData)
                myAdapter.notifyDataSetChanged()
                binding.recyclerviewRecipes.adapter = myAdapter
                }

            override fun onFailure(call: Call<Result?>, t: Throwable) {
                Log.d(TAG, "OnFailure" + t.message)
            }
        })
    }
}