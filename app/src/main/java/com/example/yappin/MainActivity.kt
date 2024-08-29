package com.example.yappin

import android.annotation.SuppressLint
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class MainActivity : AppCompatActivity() {

    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    private lateinit var googleBtn: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        val layoutbtn: Button = findViewById(R.id.button_layout)

        layoutbtn.setOnClickListener {
            // Create an intent to start the HomeActivity
            val intent = Intent(this, SetLayout::class.java)
            startActivity(intent)
        }

        //Yugaank//
        val addbtn: Button = findViewById(R.id.button_add)

        addbtn.setOnClickListener {
            // Create an intent to start the HomeActivity
            val intent = Intent(this, AddItem::class.java)
            startActivity(intent)

        }

        val addb: Button = findViewById(R.id.button_wardrobe)

        addb.setOnClickListener {
            // Create an intent to start the HomeActivity
            val intent = Intent(this, Wardrobe::class.java)
            startActivity(intent)

        }

    }

}

