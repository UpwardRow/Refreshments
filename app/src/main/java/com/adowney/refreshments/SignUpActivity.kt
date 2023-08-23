package com.adowney.refreshments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.adowney.refreshments.databinding.ActivitySignUpBinding
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener {
            val firstName = binding.firstNameTyped.text.toString()
            val lastName = binding.lastNameTyped.text.toString()
            val email = binding.emailCreateTyped.text.toString()
            val password = binding.passwordCreateTyped.text.toString()
            val confirmPassword = binding.passwordConfirmationTyped.text.toString()

            createUser(firstName, lastName, email, password, confirmPassword)

            val homeIntent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(homeIntent)
        }
    }

    private fun createUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        if (firstName.isNotEmpty() &&
            lastName.isNotEmpty() &&
            email.isNotEmpty() &&
            password.isNotEmpty() &&
            confirmPassword.isNotEmpty()
        ) {
            if (password == confirmPassword) {
                val options = AuthSignUpOptions.builder()
                    .userAttribute(AuthUserAttributeKey.email(), email)
                    .userAttribute(AuthUserAttributeKey.name(), firstName)
                    .userAttribute(AuthUserAttributeKey.familyName(), lastName)
                    .build()
                /*
                    Note: Cognito user pools store data in Amazon Cloud Directory, encrypting data
                    at rest and in transit by using 256-bit encryption keys.
                */
                Amplify.Auth.signUp(email, password, options,
                    { Log.i("AuthQuickStart", "Sign up succeeded: $it") },
                    { Log.e("AuthQuickStart", "Sign up failed", it) }
                )

                // Here I am passing the email from this activity to the fragment
                val code_fragment = Fragment()

                val bundle = Bundle().apply { putString("user_email", email) }
                code_fragment.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, code_fragment)
                    .commit()

            } else {
                Toast.makeText(
                    applicationContext,
                    "Passwords do not match",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}