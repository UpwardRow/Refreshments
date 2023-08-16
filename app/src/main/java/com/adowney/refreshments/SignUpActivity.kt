package com.adowney.refreshments

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.adowney.refreshments.databinding.ActivitySignUpBinding
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.signUpButton.setOnClickListener{
            val firstName = binding.firstNameTyped.text.toString()
            val email = binding.emailCreateTyped.text.toString()
            val password = binding.passwordCreateTyped.text.toString()
            val confirmPassword = binding.passwordConfirmationTyped.toString()

            if (firstName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    val options = AuthSignUpOptions.builder()
                        .userAttribute(AuthUserAttributeKey.email(), email)
                        .build()
                    /*
                    I don't know how the password is inserted into the database, come back to this
                    later to see if it is encrypted
                    */
                    Amplify.Auth.signUp(email, password, options,
                        { Log.i("AuthQuickStart", "Sign up succeeded: $it") },
                        { Log.e ("AuthQuickStart", "Sign up failed", it) }
                    )
                    Amplify.Auth.confirmSignUp(
                        email, "the code you received via email",
                        { result ->
                            if (result.isSignUpComplete) {
                                Log.i("AuthQuickstart", "Confirm signUp succeeded")
                            } else {
                                Log.i("AuthQuickstart","Confirm sign up not complete")
                            }
                        },
                        { Log.e("AuthQuickstart", "Failed to confirm sign up", it) }
                    )
                }
            }
        }

    }


}