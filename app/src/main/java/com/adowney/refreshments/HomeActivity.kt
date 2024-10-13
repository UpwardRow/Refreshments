package com.adowney.refreshments

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adowney.refreshments.databinding.ActivityHomeBinding
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adowney.refreshments.databinding.FilterCellsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID


class HomeActivity : AppCompatActivity(), FilterAdapter.OnFilterDeleteActionListener {

    companion object {
        private const val TAG = "HomeActivity"
        var USER_QUERY = ""
        var userFiltersList: MutableList<String> = mutableListOf()
    }

    private var uid: String? = null
    private var startTime: Long = - 1
    private lateinit var binding: ActivityHomeBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var filterDataList: ArrayList<FilterData>
    private lateinit var foodList: Array<String>
    private lateinit var context: HomeActivity
    private lateinit var recyclerViewFilters: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        firebaseAuth = FirebaseAuth.getInstance()
        uid = firebaseAuth.currentUser?.uid

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

        databaseReference = FirebaseDatabase
            .getInstance()
            .getReference("UserData")

        // Keeps set size if it conflicts with other layouts
        binding.recyclerviewFilters.setHasFixedSize(true)
        binding.recyclerviewFilters.layoutManager = LinearLayoutManager(this)

        // Making this lateinit broke the recyclerview. There is a problem with linear layout manager
        recyclerViewFilters = binding.recyclerviewFilters

        foodList = arrayOf(
            "Coconut milk"
        )

        filterDataList = arrayListOf()

        context = this

        getFilters(uid, true)

        binding.quickFiltersButton.setOnClickListener{
            val quickFiltersIntent = Intent(applicationContext, QuickFiltersActivity::class.java)

            startActivity(quickFiltersIntent)
        }

        binding.filterAddBar.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                val enteredFilter = binding.filterAddBar.text

                // This happens when 'Enter' is pressed
                Toast.makeText(this,
                    "Enter key pressed! Here is the filter $enteredFilter",
                    Toast
                    .LENGTH_SHORT)
                    .show()

                addFilter(enteredFilter.toString(), uid, recyclerViewFilters, this)

                enteredFilter.clear()

                true
            } else {
                false
            }
        }

        val filterCellsBinding = FilterCellsBinding.inflate(layoutInflater)

        filterCellsBinding.deleteButton.setOnClickListener{
            Toast.makeText(this, "Delete tapped!", Toast.LENGTH_SHORT).show()
            deleteFilter(uid, filterCellsBinding.foodFilter.text.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        //Inflating menu with items
        menuInflater.inflate(R.menu.action_bar, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        // Initialising menu item search bar, with the id and taking that object
        val searchView = menu.findItem(R.id.ic_search).actionView as SearchView
        searchView.queryHint = "Pizza"

        searchView.setOnQueryTextListener(object :  SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                Log.d(TAG, "Text change in search $newText")
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                //On submit via enter send entire query
                val resultsIntent = Intent(applicationContext, ResultsActivity::class.java)
                USER_QUERY = query
                startActivity(resultsIntent)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun deleteFilter(uid: String?, filter: String){
        val that = this

        CoroutineScope(Dispatchers.IO).launch {
            if (uid != null){

                filterDataList.clear()

                databaseReference.child("Users").child(uid).child("CustomFilters")
                    .child(filter).removeValue().addOnCompleteListener{ task ->
                        if (task.isSuccessful){
                            Toast.makeText(that,
                                "Successfully deleted $filter filter from database",
                                Toast.LENGTH_SHORT).show()

                            getFilters(uid, true)

                        } else {
                            Toast.makeText(that,
                                "Failed to delete $filter filter from database: " +
                                        "${task.exception?.message}",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                Toast.makeText(that,
                    "Can not delete filter. Uid must not be null",
                    Toast.LENGTH_SHORT).show()
            }
        }
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
                    AccountActivity::class.java)
                startActivity(accountIntent)
                true
            }

            // Handle opening account section
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Remember to add food list to companion object for access in ResultsActivity
    private fun getFilters(uid: String?, isGettingFiltersView: Boolean){
        val that = this

        CoroutineScope(Dispatchers.IO).launch {
            if (uid != null){

                filterDataList.clear()

                databaseReference.child("Users").child(uid).child("CustomFilters")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            userFiltersList = mutableListOf()
                            for (snapshot in dataSnapshot.children) {
                                userFiltersList.add(snapshot.key.toString())
                                val filterData = FilterData(snapshot.key.toString())
                                filterDataList.add(filterData)
                            }
                            /*userFilterFromDb = snapshot.key
                            filterDataList.add(filter)*/
                            Toast.makeText(that,
                                "Enter key pressed! Here are the filter(s) $foodList",
                                Toast.LENGTH_SHORT
                            ).show()
                            if (isGettingFiltersView){
                                recyclerViewFilters.adapter = FilterAdapter(filterDataList, context)
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            println("Error retrieving data: ${databaseError.message}")
                        }
                    })
            }
        }
    }

    private fun addFilter(enteredFilter: String, uid: String?,
                          recyclerView: RecyclerView, homeActivity: HomeActivity){

            if(uid != null){

                val ingredient = mapOf(
                    "timestamp" to System.currentTimeMillis(),
                    "uuid" to UUID.randomUUID().toString(),
                    "addedBy" to firebaseAuth.currentUser?.displayName.toString()
                )

                databaseReference.child("Users")
                    .child(uid)
                    .child("CustomFilters")
                    .child(enteredFilter).setValue(ingredient)

                getFilters(uid, true)
        }
    }

    override fun onDeleteFilterFromView(filter: String) {
        deleteFilter(uid, filter)
    }
}