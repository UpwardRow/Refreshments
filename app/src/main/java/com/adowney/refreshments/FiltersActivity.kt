package com.adowney.refreshments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adowney.refreshments.databinding.ActivityFiltersBinding

class FiltersActivity : AppCompatActivity() {

    val list: MutableList<String> = ArrayList()

    private lateinit var binding: ActivityFiltersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters)

        binding = ActivityFiltersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


    }
}