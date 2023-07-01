package com.adowney.refreshments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.adowney.refreshments.databinding.ActivityFiltersBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FiltersActivity : AppCompatActivity() {

    val list: MutableList<String> = ArrayList()

    private lateinit var binding: ActivityFiltersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters)

        binding = ActivityFiltersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.saveBtn.setOnClickListener{
            saveDetails()
        }

        binding.saveBtn.setOnClickListener{
            saveFilter()
        }
    }

    // Storing all strings in an ArrayList from the textView
    fun saveDetails(){
        val userText = binding.textView.text.toString()
        binding.textView.setText(userText)
        list += userText
        print(list)
    }

    private fun saveFilter(){
        // Getting the string in the textview, asking the user to input a string if left empty
        val filter = binding.textView.text.toString().trim()

        if(filter.isEmpty()){
            binding.textView.error = "Enter a filter please"
            return
        }

        val database = Firebase.database
        // Attempting to write filters to Firebase
        val ref = database.reference.child("ingredients")
        val ingredientId = ref.push().key

        val ingredients = Filterfirebase(ingredientId!!, filter)

        ref.child(ingredientId).setValue(ingredients).addOnCompleteListener{
            Toast.makeText(applicationContext, "Filter saved", Toast.LENGTH_LONG).show()
        }
    }
}