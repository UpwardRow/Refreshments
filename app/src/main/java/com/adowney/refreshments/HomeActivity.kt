package com.adowney.refreshments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import com.adowney.refreshments.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "HomeActivity"
        var USER_QUERY = ""
    }

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Configuring binding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.bringToFront()

        // Three navigational buttons in the home activity
        binding.button.setOnClickListener() {
            val intent1 = Intent(applicationContext, AccountActivity::class.java)
            startActivity(intent1)
        }

        binding.button3.setOnClickListener {
            val intent3 = Intent(applicationContext, FiltersActivity::class.java)
            startActivity(intent3)
        }

        binding.Go.setOnClickListener {
            val intentGo = Intent(applicationContext, ResultsActivity::class.java)
            // Printing the text in the search bar
            Log.d(TAG, searchView.query.toString())
            USER_QUERY = searchView.query.toString()
            startActivity(intentGo)
        }

        // Expands the searchView at the home when clicked
        searchView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                searchView.onActionViewExpanded()
            }
        })
    }
}