package com.adowney.refreshments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.SearchView
import com.adowney.refreshments.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "HomeActivity"
        var USER_QUERY = ""
    }

    private lateinit var binding: ActivityHomeBinding
    private var startTime: Long = - 1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        startTime = System.currentTimeMillis()

        /*
            When the phone opens with the splash screen, it depends on the first frame of the root
            activity for the splash to end. For Emanate this is near instant. To bring more
            attention to the splash screen, I added 3 seconds on top of the first frame to load.
            This may be seen to worsen the performance but is better quality of life in my opinion.

            NOTE THAT SPLASH SCREENS IN THIS IMPLEMENTATION WILL ONLY WORK ON ANDROID 12 (API 31)
            BECAUSE FORMER VERSIONS NEEDED USER IMPLEMENTATION WITHOUT GOOGLE'S OWN FUNCTIONS
         */

        val content: View = findViewById(android.R.id.content)

        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener{
                override fun onPreDraw(): Boolean {
                    val isReady = System.currentTimeMillis() - startTime > 1000
                    // Check if the screen is ready
                    return if (isReady) {
                        // The content is ready, start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content is not ready, wait.
                        false
                    }
                }
            }
        )

        // Configuring binding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.bringToFront()

        // Three navigational buttons in the home activity
        binding.button.setOnClickListener() {
            val accountIntent = Intent(applicationContext, AccountActivity::class.java)
            startActivity(accountIntent)
        }

        binding.button3.setOnClickListener {
            val filtersIntent = Intent(applicationContext, FiltersActivity::class.java)
            startActivity(filtersIntent)
        }

        binding.Go.setOnClickListener {
            val resultsIntent = Intent(applicationContext, ResultsActivity::class.java)
            // Printing the text in the search bar
            Log.d(TAG, searchView.query.toString())
            USER_QUERY = searchView.query.toString()
            startActivity(resultsIntent)
        }

        // Expands the searchView at the home when clicked
        searchView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                searchView.onActionViewExpanded()
            }
        })
    }
}