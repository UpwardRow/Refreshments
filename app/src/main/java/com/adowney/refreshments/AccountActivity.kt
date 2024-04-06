package com.adowney.refreshments

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils


class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true);
            supportActionBar?.setDisplayShowHomeEnabled(true);
            supportActionBar?.title = "Account"
        } else {
            throw NullPointerException("Action bar is null");
        }
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
           /* R.id.notifications_button -> {
                val accountIntent = Intent(applicationContext, AccountActivity::class.java);
                startActivity(accountIntent);
                true;
            }*/
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true;
            }
            // Handle opening account section
            else -> super.onOptionsItemSelected(item)
        }
    }
}