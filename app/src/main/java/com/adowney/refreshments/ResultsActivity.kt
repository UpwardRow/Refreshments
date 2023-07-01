package com.adowney.refreshments
//
import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log.d
//import androidx.recyclerview.widget.LinearLayoutManager
//import kotlinx.android.synthetic.main.activity_home.*
//import kotlinx.android.synthetic.main.activity_results.*
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//// Start of the Spoonacular URL
//const val spoonacularBaseURL = "https://api.spoonacular.com/"
//
class ResultsActivity : AppCompatActivity() {
//
//    lateinit var myAdapter: MyAdapter
//    lateinit var linearLayoutManager: LinearLayoutManager
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_results)
//
//        recyclerview_recipes.setHasFixedSize(true)
//        linearLayoutManager = LinearLayoutManager(this)
//        recyclerview_recipes.layoutManager = linearLayoutManager
//
//        getSpoonacularData()
//        supportActionBar?.hide()
//    }
//
//    private fun getSpoonacularData(){
//        val retrofitBuilder = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(spoonacularBaseURL)
//            .build()
//            .create(APIInterface::class.java)
//
//        //Attempting to read ArrayList from FiltersActivity
//        val filtersObject = FiltersActivity()
//        val squareBracketsCharacter = 1
//        println(filtersObject.list)
//        val newList = filtersObject.list.toString()
//        val result = newList.drop(squareBracketsCharacter)
//        val lastResult = result.drop(squareBracketsCharacter)
//        val arrayListString = lastResult.filter { !it.isWhitespace() }
//
//        /*This was supposed to be used for the searchbar at the home to concatenate the query to the
//        GET request
//        */
//        val bar = searchView
//
//        val retrofitData = retrofitBuilder.getSpoonacularData("cheese", "Burger")
//
//        //Creates the row data for the recyclerview
//        retrofitData.enqueue(object : Callback<SpoonacularData?> {
//            override fun onResponse(
//                call: Call<SpoonacularData?>,
//                response: Response<SpoonacularData?>
//            ) {
//                val responseBody = response.body()!!
//
//                myAdapter = MyAdapter(baseContext, responseBody)
//                myAdapter.notifyDataSetChanged()
//                recyclerview_recipes.adapter = myAdapter
//            }
//
//            //Outputs the error if failed
//            override fun onFailure(call: Call<SpoonacularData?>, t: Throwable) {
//                d("ResultsActivity", "onFailure: "+t.message)
//            }
//        })
//    }
}