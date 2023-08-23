package com.adowney.refreshments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import com.adowney.refreshments.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "SignInActivity"
    }

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.notRegistered.setOnClickListener(){
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }

//        binding.notRegistered.setOnClickListener {
//            val intent = Intent(this, SignUpActivity::class.java)
//            finish()
//            startActivity(intent)
//        }

    }
}