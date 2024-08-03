package com.adowney.refreshments.fragments


import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adowney.refreshments.databinding.FragmentConfirmSignInBinding
import com.adowney.refreshments.R
import com.amplifyframework.core.Amplify

class ConfirmSignInFragment : Fragment() {

    private lateinit var binding: FragmentConfirmSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState)

        val view = inflater.inflate(
            R.layout.fragment_confirm_sign_in,
            container, false
        )

        val emailFromActivity = arguments?.getString("user_email")
        confirmSignUpUser(emailFromActivity)

        return view
    }

    private fun confirmSignUpUser(email: String?) {
        if (email != null) {
            Amplify.Auth.confirmSignUp(
                email, "the code you received via email",
                { result ->
                    if (result.isSignUpComplete) {
                        Log.i("AuthQuickstart", "Confirm signUp succeeded")
                    } else {
                        Log.i("AuthQuickstart", "Confirm sign up not complete")
                    }
                },
                { Log.e("AuthQuickstart", "Failed to confirm sign up", it) }
            )
        }
    }
}