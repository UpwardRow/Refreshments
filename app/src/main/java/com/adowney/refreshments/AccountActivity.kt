package com.adowney.refreshments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.InputFilter
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.adowney.refreshments.databinding.ActivityAccountBinding
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AccountActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "AccountActivity"
    }

    private lateinit var binding: ActivityAccountBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference

    private var imagePickerActivityResult: ActivityResultLauncher<Intent> =
        /*
        Lambda expression receives the result, I am receiving one single
        image on selection
        */
        registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) { result ->
            if (result != null) {

                val imageUri: Uri? = result.data?.data
                val imageFileName = getFileName(applicationContext, imageUri!!)

                // Upload Task with upload to directory 'file'
                // and name of the file remains same
                val uploadTask = storageReference
                    .child(
                        "Users/" + firebaseAuth.currentUser?.uid + "/ProfilePicture/" +
                                "/$imageFileName"
                    ).putFile(imageUri)

                // On success, download the file URL and display it
                uploadTask.addOnSuccessListener {
                    // Using glide library to display the image
                    storageReference.child(
                        "Users/" + firebaseAuth.currentUser?.uid + "/ProfilePicture/" +
                                "/$imageFileName"
                    ).downloadUrl.addOnSuccessListener {
                        Glide.with(this@AccountActivity)
                            .load(it)
                            .into(binding.profilePicture)

                        val profileUpdates = userProfileChangeRequest {
                            photoUri = it
                        }

                        firebaseAuth.currentUser!!.updateProfile(profileUpdates)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.e(
                                        "Firebase",
                                        "Download complete and profile updated"
                                    )
                                }
                            }

                    }.addOnFailureListener {
                        Log.e("Firebase", "Failed in downloading")
                    }
                }.addOnFailureListener {
                    Log.e("Firebase", "Image Upload fail")
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        //Firebase setup parameters
        firebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseAuth.currentUser?.uid;

        if (firebaseAuth.currentUser != null) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_account)
            binding = ActivityAccountBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val firstNameField = binding.firstName
            val lastNameField = binding.lastName
            val usernameField = binding.username
            val bioField = binding.bio

            firstNameField.filters = arrayOf(InputFilter.LengthFilter(30))
            lastNameField.filters = arrayOf(InputFilter.LengthFilter(30))
            usernameField.filters = arrayOf(InputFilter.LengthFilter(30))
            bioField.filters = arrayOf(InputFilter.LengthFilter(150))

            // This is setting up the actionbar
            if (supportActionBar != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowHomeEnabled(true)
                supportActionBar?.setHomeAsUpIndicator(
                    R.drawable.action_bar_arrow_back
                )
                supportActionBar?.title = "Account"
                supportActionBar
            } else {
                throw NullPointerException("Action bar is null");
            }

            databaseReference = FirebaseDatabase
                .getInstance()
                .getReference("UserData")

            val storage = Firebase.storage

            storageReference = storage.reference

            // Setting profile picture as picture in account activity
            Glide.with(this@AccountActivity)
                .load(firebaseAuth.currentUser?.photoUrl)
                .into(binding.profilePicture)

            val uploadProfilePicture = binding.uploadProfilePicture;

            uploadProfilePicture.setOnClickListener {

                // This ACTION_PICK picks from data, returning the selected item
                val galleryIntent = Intent(Intent.ACTION_PICK)

                // Item of type image
                galleryIntent.type = "image/*"

                imagePickerActivityResult.launch(galleryIntent)
            }

            loadAccountDetails(databaseReference, uid, firstNameField, lastNameField,
                usernameField, bioField)

            binding.saveAccountUpdate.setOnClickListener {
                updateAccountDetails(uid, firstNameField, lastNameField, usernameField, bioField)
            }
        }
    }

    private fun loadAccountDetails(
        databaseReference: DatabaseReference, uid: String?,
        firstNameField: TextInputEditText, lastNameField:
        TextInputEditText, usernameField: TextInputEditText,
        bioField: EditText
    ) {

        CoroutineScope(Dispatchers.IO).launch {
            if (uid != null) {
                databaseReference.child("Users").child(uid).child("firstName")
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val firstNameValue = snapshot.getValue(String::class.java)
                            firstNameValue?.let {
                                firstNameField.setText(it)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.e(
                                TAG, "Could not populate firstNameField with user first name"
                            )
                        }
                    })
                databaseReference.child("Users").child(uid).child("lastName")
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val lastNameValue = snapshot.getValue(String::class.java)
                            lastNameValue?.let {
                                lastNameField.setText(it)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.e(
                                TAG, "Could not populate lastNameField with user last name"
                            )
                        }
                    })
                databaseReference.child("Users").child(uid).child("displayName")
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val usernameValue = snapshot.getValue(String::class.java)
                            usernameValue?.let {
                                usernameField.setText(it)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.e(
                                TAG, "Could not populate usernameField with user username"
                            )
                        }
                    })
                databaseReference.child("Users").child(uid).child("bio")
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val bioValue = snapshot.getValue(String::class.java)
                            bioValue?.let {
                                bioField.setText(it)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.e(
                                TAG, "Could not populate firstNameField with user first name"
                            )
                        }
                    })
            }
        }
    }

    private fun updateAccountDetails(
        uid: String?, firstNameField: TextInputEditText, lastNameField:
        TextInputEditText, usernameField: TextInputEditText, bioField: EditText
    ) {

        if (uid != null) {

            Log.e(TAG, "Uid is $uid")

            // Updating firstname
            Log.e(
                TAG, "firstName text is " + firstNameField.text
            )

            databaseReference.child("Users").child(uid).child("firstName").setValue(
                firstNameField
                    .text.toString()
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.e(
                            TAG,
                            "firstName text is set to " + firstNameField.text + " in database"
                        )
                    } else {
                        Log.e(TAG, "firstName text could not be updated")
                    }
                }

            // Updating lastname
            Log.e(
                TAG, "lastName text is " + lastNameField.text
            )

            databaseReference.child("Users").child(uid).child("lastName")
                .setValue(lastNameField.text.toString()).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.e(
                            TAG, "lastName text is set to " + firstNameField.text + " " +
                                    "in database"
                        )
                    } else {
                        Log.e(TAG, "lastName text could not be updated")
                    }
                }

            // Updating bio
            Log.e(
                TAG, "Bio is " + bioField.text
            )

            databaseReference.child("Users").child(uid).child("bio")
                .setValue(bioField.text.toString()).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.e(
                            TAG, "Bio text is set to " + bioField.text + " " +
                                    "in database"
                        )
                    } else {
                        Log.e(TAG, "Bio could not be updated")
                    }
                }

            updateUsername(usernameField.text.toString(), uid)

        }
    }

    private fun getFileName(context: Context, uri: Uri): String? {
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(
                uri,
                null,
                null,
                null,
                null
            )
            cursor.use {
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        return cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                OpenableColumns
                                    .DISPLAY_NAME
                            )
                        )
                    }
                }
            }
        }
        return uri.path?.lastIndexOf('/')?.let { uri.path?.substring(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true;
            }
            // Handle opening account section
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun overwriteUsername(uid: String) {
        databaseReference.child("Usernames")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    var found = false

                    databaseReference.child("Users")
                        .child(uid)
                        .child("displayName")
                        .setValue(firebaseAuth.currentUser?.displayName)

                    for (dataSnapshot in snapshot.children) {
                        val value = dataSnapshot.getValue(String::class.java)
                        if (value == uid) {

                            // Found a match
                            val key = dataSnapshot.key
                            println("Found match at key: $key")

                            // Get the existing data
                            val data = dataSnapshot.value

                            // Create a map to hold the updates
                            val updates = mutableMapOf<String, Any?>()
                            updates[firebaseAuth.currentUser?.displayName.toString()] = data
                            updates[key!!] = null // Mark the old key for deletion

                            // Perform the update
                            databaseReference.child("Usernames").updateChildren(updates)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        println("Updated key from $key to " + firebaseAuth.currentUser?.displayName.toString())
                                    } else {
                                        println("Error updating key: ${task.exception?.message}")
                                    }
                                }

                            found = true;
                            break
                        }
                    }
                    if (!found) {
                        println("No match found")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Error querying database: ${error.message}")
                }
            })
    }

    private fun updateUsername(usernameField: String, uid: String) {
        // Need this to run on another thread as it's affecting the main thread for the username
        // updates
        CoroutineScope(Dispatchers.IO).launch {
            // Updating username
            Log.e(
                TAG, "Username is $usernameField"
            )

            if(usernameField != firebaseAuth.currentUser?.displayName){
                val profileUpdates = userProfileChangeRequest {
                    displayName = usernameField
                }

                firebaseAuth.currentUser!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.e(
                            "Firebase",
                            "Display name updated to " +
                                    firebaseAuth.currentUser?.displayName
                        )

                        overwriteUsername(uid)

                    } else {
                        task.exception?.let {
                            println("Error updating document: ${it.message}")
                        }
                    }
                }.addOnFailureListener { exception ->
                    Log.e("Firebase", "Failed to update profile: ${exception.message}")
                }.addOnSuccessListener {
                    Log.e("Firebase", "Successfully updated profile.")
                }
            }
        }
    }
}