package com.adowney.refreshments

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.adowney.refreshments.databinding.ActivityHomeBinding
import androidx.appcompat.widget.SearchView


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
                        content.viewTreeObserver
                            .removeOnPreDrawListener(this)
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
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        //Inflating menu with items
        menuInflater.inflate(R.menu.action_bar, menu);

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        // Initialising menu item search bar, with the id and taking that object
        val searchView = menu.findItem(R.id.ic_search).actionView as SearchView
        searchView.queryHint = "Pizza"

        searchView.setOnQueryTextListener(object :  SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                Log.d(TAG, "Text change in search");
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                //On submit via enter send entire query
                Log.d(TAG, searchView.query.toString());
                USER_QUERY = searchView.query.toString();
                return false
            }
        })
        return super.onCreateOptionsMenu(menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.ic_search -> {
                // Handling the search action
                val searchView = R.id.ic_search

                Log.d(TAG, "Search being used")
                true
            }
            R.id.notifications_button -> {
                val accountIntent = Intent(applicationContext,
                    AccountActivity::class.java);
                startActivity(accountIntent);
                true;
            }

            // Handle opening account section
            else -> super.onOptionsItemSelected(item)
        }
    }
}