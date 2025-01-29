package com.adowney.refreshments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class QuickFiltersViewModel : ViewModel() {

    companion object {
        private const val TAG = "QuickFiltersViewModel"
    }

    private val _dataFlow = MutableStateFlow<MutableMap<String, Any?>?>(null)
    val dataFlow: StateFlow<MutableMap<String, Any?>?> = _dataFlow

    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("UserData")
    private var firebaseAuth = FirebaseAuth.getInstance()
    private var uid = firebaseAuth.currentUser?.uid.toString()

    private val quickFiltersList = PredefinedQuickFilters.getAllQuickFilters()
    private val quickFiltersListNames = quickFiltersList.map { it.quickFilterName }

    fun searchForQuickFilters(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val databaseQuickFilters = fetchQuickFiltersFromFirebase(quickFiltersListNames)

                withContext(Dispatchers.Main){
                    _dataFlow.value = databaseQuickFilters
                }
            }catch (e: Exception) {
                Log.e(TAG, "Error fetching filters: ${e.message}")
            }
        }
    }

   private suspend fun fetchQuickFiltersFromFirebase(quickFiltersListNames: List<String>): MutableMap<String, Any?>{

       /*
       This CompletableDeferred will essentially be used to turn my Firebase callback into a
       coroutine suspend function
        */
       val deferred = CompletableDeferred<MutableMap<String, Any?>>()

       databaseReference.child("Users").child(uid).child("QuickFilters")
           .addListenerForSingleValueEvent(object : ValueEventListener {
               val quickFiltersMap = mutableMapOf<String, Any?>()
               override fun onDataChange(snapshot: DataSnapshot) {
                   Log.d(TAG, "onDataChange: Called")
                   if (snapshot.value != null){
                       for (dataSnapshot in snapshot.children) {
                           Log.d(TAG, "Going through snapshot")
                           val key = dataSnapshot.key
                           val value = dataSnapshot.value
                           Log.d(TAG, "HERE IS KEY: $key")
                           if (key.toString() in quickFiltersListNames) {
                               print("Found quick filter at key: $key")
                               Log.d(TAG, quickFiltersMap.toString())
                               if (key != null) {
                                   quickFiltersMap[key] = value
                               }
                           }
                           deferred.complete(quickFiltersMap)
                       }
                   } else {
                       quickFiltersMap[""] = ""
                       deferred.complete(quickFiltersMap)
                   }
               }

               override fun onCancelled(error: DatabaseError) {
                   deferred.completeExceptionally(Exception(error.message))
               }
           })
       // Await for the result from Firebase callback
       return deferred.await()
   }
}