package com.adowney.refreshments.fragments


import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adowney.refreshments.databinding.FragmentConfirmSignInBinding
import com.adowney.refreshments.R

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

        return view
    }
}