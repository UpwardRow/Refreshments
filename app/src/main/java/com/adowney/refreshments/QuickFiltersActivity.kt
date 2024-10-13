package com.adowney.refreshments

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adowney.refreshments.databinding.ActivityQuickFiltersBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuickFiltersActivity: AppCompatActivity(),
    QuickFilterAdapter.OnQuickFilterCheckedActionListener {

    companion object {
        private const val TAG = "QuickFiltersActivity"
    }

    private var uid: String? = null
    private lateinit var binding: ActivityQuickFiltersBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var context: QuickFiltersActivity
    private lateinit var quickFiltersList: List<PredefinedQuickFilters>
    private lateinit var quickFiltersListNames: List<String>
    private lateinit var quickFiltersListApiLabels: List<String>
    private lateinit var quickFiltersViewModel: QuickFiltersViewModel
    private lateinit var databaseQuickFilters: MutableMap<String, Any?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quickFiltersViewModel = ViewModelProvider(this)[QuickFiltersViewModel::class.java]
        databaseQuickFilters = mutableMapOf()

        context = this

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase
            .getInstance()
            .getReference("UserData")

        uid = firebaseAuth.currentUser?.uid

        binding = ActivityQuickFiltersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerviewQuickFilters = binding.recyclerviewQuickFilters
        recyclerviewQuickFilters .setHasFixedSize(true)
        recyclerviewQuickFilters .layoutManager = LinearLayoutManager(this)

        quickFiltersList = PredefinedQuickFilters.getAllQuickFilters()
        quickFiltersListNames = quickFiltersList.map { it.quickFilterName }

        lifecycleScope.launch {
            quickFiltersViewModel.dataFlow.collect { data ->
                if (data != null){
                    databaseQuickFilters = data

                    getQuickFilters(recyclerviewQuickFilters, context)
                } else {
                    quickFiltersViewModel.searchForQuickFilters()
                }
            }
        }
    }

    private fun addQuickFilterToUser(checkedDietaryFilter: String, checked: Boolean, uid: String?) {
        val that = this
        if (uid != null) {

            CoroutineScope(Dispatchers.IO).launch {
                quickFiltersListApiLabels = quickFiltersList.map { it.apiLabel }
                if (checkedDietaryFilter in quickFiltersListNames) {
                    val apiLabelOfQuickFilter = PredefinedQuickFilters
                        .getApiLabelByName(checkedDietaryFilter)
                    if (checked) {
                        Log.d(TAG, "$checkedDietaryFilter added to user database!")
                        databaseReference.child("Users").child(uid).child("QuickFilters")
                            .child(checkedDietaryFilter).setValue(apiLabelOfQuickFilter)

                        quickFiltersViewModel.searchForQuickFilters()
                    } else {
                        databaseReference.child("Users").child(uid).child("QuickFilters")
                            .child(checkedDietaryFilter).removeValue().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        that,
                                        "Successfully deleted $checkedDietaryFilter quick filter from database",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    quickFiltersViewModel.searchForQuickFilters()
                                } else {
                                    Toast.makeText(
                                        that,
                                        "Failed to delete $checkedDietaryFilter quick filter from database: " +
                                                "${task.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                }
            }
        }
        else {
            Toast.makeText( that,"Could not add or delete quick filter. Uid must not be null",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun getQuickFilters(recyclerView: RecyclerView, context: QuickFiltersActivity){
        val databaseQuickFiltersNames = databaseQuickFilters.keys.toList()
        val databaseQuickFiltersStrings = databaseQuickFiltersNames.map { it }

        recyclerView.adapter = QuickFilterAdapter(databaseQuickFiltersStrings, context)
        val quickFilterAdapter = QuickFilterAdapter(databaseQuickFiltersStrings, context)
        Log.d("Item count quick filter", quickFilterAdapter.itemCount.toString())
    }

    override fun onCheckedQuickFilterFromView(quickFilter: String, checked: Boolean) {
        addQuickFilterToUser(quickFilter, checked, uid)
    }
}