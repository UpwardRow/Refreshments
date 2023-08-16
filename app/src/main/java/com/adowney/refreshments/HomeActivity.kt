package com.adowney.refreshments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import com.adowney.refreshments.databinding.ActivityHomeBinding
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.core.Amplify

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "HomeActivity"
        var USER_QUERY = ""
    }

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Configuring binding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.bringToFront()

        // Three navigational buttons in the home activity
        binding.button.setOnClickListener() {
            val intent1 = Intent(applicationContext, AccountActivity::class.java)
            startActivity(intent1)
        }

        binding.button3.setOnClickListener {
            val intent3 = Intent(applicationContext, FiltersActivity::class.java)
            startActivity(intent3)
        }

        binding.Go.setOnClickListener {
            val intentGo = Intent(applicationContext, ResultsActivity::class.java)
            // Printing the text in the search bar
            Log.d(TAG, searchView.query.toString())
            USER_QUERY = searchView.query.toString()
            startActivity(intentGo)
        }

        // Expands the searchView at the home when clicked
        searchView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                searchView.onActionViewExpanded()
            }
        })

        /*
            Amplify auth session, "The isSignedIn property of the authSession will be false since
            you haven't signed in to the category yet."
         */
        Amplify.Auth.fetchAuthSession(
            {
                val session = it as AWSCognitoAuthSession
                when (session.identityIdResult.type) {
                    AuthSessionResult.Type.SUCCESS ->
                        Log.i("AuthQuickStart", "IdentityId = ${session.identityIdResult.value}")
                    AuthSessionResult.Type.FAILURE ->
                        Log.w("AuthQuickStart", "IdentityId not found", session.identityIdResult.error)
                }
            },
            { Log.e("AuthQuickStart", "Failed to fetch session", it) }
        )

        val currentUser  = Amplify.Auth.getCurrentUser(
            { Log.i("AmplifyQuickstart", "Auth session = $it") },
            { error -> Log.e("AmplifyQuickstart", "Failed to fetch auth session", error)}
        )

//        if(currentUser){
//            val intentSignIn = Intent(applicationContext, SignInActivity::class.java)
//            startActivity(intentSignIn)
//        }
    }
}