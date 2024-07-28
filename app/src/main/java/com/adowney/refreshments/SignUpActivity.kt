package com.adowney.refreshments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adowney.refreshments.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SignUpActivity"
    }

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        databaseReference = FirebaseDatabase
            .getInstance()
            .getReference("UserData")

        // With this listener the user can click if they already have an account
        binding.alreadyRegistered.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            finish()
            startActivity(intent)
        }

        binding.signUpButton.setOnClickListener {
            val email = binding.emailCreateTyped.text.toString()
            val password = binding.passwordCreateTyped.text.toString()
            val confirmPassword = binding.passwordConfirmationTyped.text
                .toString()
            val usernameField = binding.usernameTyped.text.toString()

            /*
            All of the conditions inside this statement are for verifying the credentials entered
            with the users on Firebase
            */
            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() &&
                usernameField.isNotEmpty()) {
                if (password == confirmPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful && firebaseAuth.currentUser?.uid != null) {
                                createUsername(usernameField)

                                val intent = Intent(this, SignInActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this,
                                    it.exception.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        this,
                        "Passwords do not match",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Fields must not be empty",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun createUsername(usernameField: String) {
        val uid = firebaseAuth.currentUser?.uid

        Log.e(
            TAG, "Username is $usernameField"
        )

        val profileUpdates = userProfileChangeRequest {
            displayName = usernameField
        }

        firebaseAuth.currentUser!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.e(
                    "Firebase",
                    "Display name is now " +
                            firebaseAuth.currentUser?.displayName
                )
            } else {
                task.exception?.let {
                    println("Error creating displayName: ${it.message}")
                }
            }
        }.addOnFailureListener { exception ->
            Log.e("Firebase", "Failed to update profile: ${exception.message}")
        }.addOnSuccessListener {
            Log.e("Firebase", "Successfully created profile")
        }

        val dbPathToUsernames = databaseReference.child("Usernames")

        dbPathToUsernames.child(usernameField).setValue(uid)

        Log.e(
            TAG,"Displayname is " +
                    firebaseAuth.currentUser?.displayName
        )

        CoroutineScope(Dispatchers.IO).launch {
            val dbPathToUserInformation =
                databaseReference.child("Users")

            if (uid != null) {
                dbPathToUserInformation
                    .child(uid)
                    .child("displayName")
                    .setValue(usernameField)
            }
        }
    }
}