package com.adowney.refreshments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SearchView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()

        val searchView = findViewById<SearchView>(R.id.searchView)
        val b1 = findViewById<Button>(R.id.button)
        val b3 = findViewById<Button>(R.id.button3)
        val go = findViewById<Button>(R.id.Go)

        searchView.bringToFront()

        // Three navigational buttons in the home activity
        b1.setOnClickListener {
            val intent1 = Intent(applicationContext, AccountActivity::class.java)
            startActivity(intent1)
        }

        b3.setOnClickListener {
            val intent3 = Intent(applicationContext, FiltersActivity::class.java)
            startActivity(intent3)
        }

        go.setOnClickListener {
            val intentGo = Intent(applicationContext, ResultsActivity::class.java)
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