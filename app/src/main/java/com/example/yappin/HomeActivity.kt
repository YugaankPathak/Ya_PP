package com.example.yappin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.yappin.SetLayout

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val layoutbtn: Button = findViewById(R.id.button_layout)
        layoutbtn.setOnClickListener {
            // Create an intent to start the HomeActivity
            val intent = Intent(this, SetLayout::class.java)
            startActivity(intent)
        }
    }
}
