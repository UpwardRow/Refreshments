package com.adowney.refreshments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adowney.refreshments.HomeActivity.Companion.USER_QUERY
import com.adowney.refreshments.databinding.ActivityResultsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ResultsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ResultsActivity"

        // Start of the Edamam URL
        private const val EDAMAM_BASE_URL = "https://api.edamam.com/"

        // This is where the API key and app is for the url, which is hidden from potential threats
        private const val APP_KEY = BuildConfig.EDAMAM_APP_KEY
        private const val APP_ID = BuildConfig.EDAMAM_APP_ID
    }

    private lateinit var binding: ActivityResultsBinding
    lateinit var searchAdapter: SearchAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var context: Context
    private lateinit var uid: String
    private lateinit var databaseQuickFilters: MutableMap<String, Any?>
    private lateinit var quickFiltersViewModel: QuickFiltersViewModel
    private lateinit var recyclerViewRecipes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quickFiltersViewModel = ViewModelProvider(this)[QuickFiltersViewModel::class.java]
        databaseQuickFilters = mutableMapOf()

        setContentView(R.layout.activity_results)

        context = this

        firebaseAuth = FirebaseAuth.getInstance()
        uid = firebaseAuth.currentUser?.uid.toString()

        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // This will tell the view to not change its size if it conflicts with other layouts
        binding.recyclerviewRecipes.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(this)

        recyclerViewRecipes = binding.recyclerviewRecipes
        recyclerViewRecipes.layoutManager = linearLayoutManager

        databaseReference = FirebaseDatabase
            .getInstance()
            .getReference("UserData")

        lifecycleScope.launch {
            quickFiltersViewModel.dataFlow.collect { data ->
                if (data != null){
                    databaseQuickFilters = data

                    getMyEdamamData()
                } else {
                    quickFiltersViewModel.searchForQuickFilters()
                }
            }
        }
    }

    private fun getMyEdamamData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(EDAMAM_BASE_URL)
            .build()
            .create(APIInterface::class.java)

        val databaseQuickFiltersApiLabels = databaseQuickFilters.values.toList()

        val databaseQuickFiltersStrings = databaseQuickFiltersApiLabels.mapNotNull { it?.toString() }

        Log.d(TAG, databaseQuickFiltersStrings.toString())

        val retrofitData = retrofitBuilder.getEdamamData(
            APP_KEY,
            APP_ID,
            "public",
            databaseQuickFiltersStrings,
            USER_QUERY,
            HomeActivity.userFiltersList)

        Log.d(TAG, "User filter data is " + HomeActivity.userFiltersList)
        Log.d(TAG, "retrofitData is $databaseQuickFilters")

        retrofitData.enqueue(object : Callback<Result?> {
            override fun onResponse(
                call: Call<Result?>,
                response: Response<Result?>
            ) {
                val responseBodyAPIData = response.body()!!

                searchAdapter = SearchAdapter(context, responseBodyAPIData)
                searchAdapter.notifyDataSetChanged()
                recyclerViewRecipes.adapter = searchAdapter
            }

            override fun onFailure(call: Call<Result?>, t: Throwable) {
                Log.d(TAG, "OnFailure" + t.message)
            }
        })
    }
}