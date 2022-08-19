package com.example.ca1_refreshments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_filters.*

class FiltersActivity : AppCompatActivity() {

    val list: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters)

        supportActionBar?.hide()

        saveBtn.setOnClickListener{
            saveDetails()
        }

        saveBtn.setOnClickListener{
            saveFilter()
        }
    }

    //Storing all strings in an ArrayList from the textView
    fun saveDetails(){
        val userText = textView.text.toString()
        textView.setText(userText)
        list += userText
        print(list)
    }

    private fun saveFilter(){
        //Getting the string in the textview, asking the user to input a string if left empty
        val filter = textView.text.toString().trim()

        if(filter.isEmpty()){
            textView.error = "Enter a filter please"
            return
        }

        //Attempting to write filters to Firebase
        val ref = FirebaseDatabase.getInstance().getReference("ingredients")
        val ingredientId = ref.push().key

        val ingredients = Filterfirebase(ingredientId!!, filter)

        ref.child(ingredientId).setValue(ingredients).addOnCompleteListener{
            Toast.makeText(applicationContext, "Filter saved", Toast.LENGTH_LONG).show()
        }
    }
}