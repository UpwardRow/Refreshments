package com.adowney.refreshments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

       /* val toolbar: Toolbar = findViewById<View>(R.id.account_toolbar) as Toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        supportActionBar?.title = "Refreshments"*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.ic_search -> {
                // Handling the search action
                true
            }
            R.id.notifications_button -> {
                val accountIntent = Intent(applicationContext, AccountActivity::class.java);
                startActivity(accountIntent);
                true;
            }
            // Handle opening account section
            else -> super.onOptionsItemSelected(item)
        }
    }
}