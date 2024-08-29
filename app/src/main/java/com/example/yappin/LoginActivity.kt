package com.example.yappin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity) // Set the layout first

        val loginButton: Button = findViewById(R.id.login) // Now find the view
        loginButton.setOnClickListener {
            // Create an intent to start the HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}