package com.example.yappin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class SetLayout : ComponentActivity() {

    private lateinit var imageView1: ImageView
    private lateinit var imageView2: ImageView
    private lateinit var imageView3: ImageView
    private lateinit var imageView4: ImageView
    private lateinit var imageView5: ImageView
    private lateinit var imageView6: ImageView
    private lateinit var imageView7: ImageView
    private lateinit var imageView8: ImageView

    private lateinit var imageView21: ImageView
    private lateinit var imageView22: ImageView
    private lateinit var imageView23: ImageView
    private lateinit var imageView24: ImageView
    private lateinit var imageView25: ImageView
    private lateinit var imageView26: ImageView
    private lateinit var imageView27: ImageView
    private lateinit var imageView28: ImageView

    private lateinit var imageView31: ImageView
    private lateinit var imageView32: ImageView
    private lateinit var imageView33: ImageView
    private lateinit var imageView34: ImageView
    private lateinit var imageView35: ImageView
    private lateinit var imageView36: ImageView
    private lateinit var imageView37: ImageView
    private lateinit var imageView38: ImageView


    private val imageViewToLauncherMap = mutableMapOf<ImageView, ActivityResultLauncher<Intent>>()
    private val REQUEST_CODE_PERMISSIONS = 1001
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity)

        val screenshotButton = findViewById<Button>(R.id.save_button)
        screenshotButton.setOnClickListener { takeScreenshot() }
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("MainActivity", "Permission granted")
            } else {
                Log.d("MainActivity", "Permission denied")
            }
        }
        imageView1 = findViewById(R.id.imageView1)
        imageView2 = findViewById(R.id.imageView2)
        imageView3 = findViewById(R.id.imageView3)
        imageView4 = findViewById(R.id.imageView4)
        imageView5 = findViewById(R.id.imageView5)
        imageView6 = findViewById(R.id.imageView6)
        imageView7 = findViewById(R.id.imageView7)
        imageView8 = findViewById(R.id.imageView8)


        initializeImageViewLauncher(imageView1)
        initializeImageViewLauncher(imageView2)
        initializeImageViewLauncher(imageView3)
        initializeImageViewLauncher(imageView4)
        initializeImageViewLauncher(imageView5)
        initializeImageViewLauncher(imageView6)
        initializeImageViewLauncher(imageView7)
        initializeImageViewLauncher(imageView8)
        //row 2
        imageView21 = findViewById(R.id.imageView21)
        imageView22 = findViewById(R.id.imageView22)
        imageView23 = findViewById(R.id.imageView23)
        imageView24 = findViewById(R.id.imageView24)
        imageView25 = findViewById(R.id.imageView25)
        imageView26 = findViewById(R.id.imageView26)
        imageView27 = findViewById(R.id.imageView27)
        imageView28 = findViewById(R.id.imageView28)


        initializeImageViewLauncher(imageView21)
        initializeImageViewLauncher(imageView22)
        initializeImageViewLauncher(imageView23)
        initializeImageViewLauncher(imageView24)
        initializeImageViewLauncher(imageView25)
        initializeImageViewLauncher(imageView26)
        initializeImageViewLauncher(imageView27)
        initializeImageViewLauncher(imageView28)

        imageView31 = findViewById(R.id.imageView31)
        imageView32 = findViewById(R.id.imageView32)
        imageView33 = findViewById(R.id.imageView33)
        imageView34 = findViewById(R.id.imageView34)
        imageView35 = findViewById(R.id.imageView35)
        imageView36 = findViewById(R.id.imageView36)
        imageView37 = findViewById(R.id.imageView37)
        imageView38 = findViewById(R.id.imageView38)


        initializeImageViewLauncher(imageView31)
        initializeImageViewLauncher(imageView32)
        initializeImageViewLauncher(imageView33)
        initializeImageViewLauncher(imageView34)
        initializeImageViewLauncher(imageView35)
        initializeImageViewLauncher(imageView36)
        initializeImageViewLauncher(imageView37)
        initializeImageViewLauncher(imageView38)




    }

    private fun takeScreenshot() {

            val rootView: View = window.decorView.findViewById(android.R.id.content)
            rootView.isDrawingCacheEnabled = true
            val bitmap: Bitmap = Bitmap.createBitmap(rootView.drawingCache)
            rootView.isDrawingCacheEnabled = false

            try {
                val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "screenshot_${System.currentTimeMillis()}.png")
                FileOutputStream(file).use {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
                    Log.d("MainActivity", "Screenshot saved to ${file.absolutePath}")
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("MainActivity", "Failed to save screenshot: ${e.message}")

        }

    }
    private fun initializeImageViewLauncher(imageView: ImageView) {
        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    // Load the image from the URI
                    val inputStream = contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    imageView.setImageBitmap(bitmap)
                }
            }
        }

        imageViewToLauncherMap[imageView] = launcher

        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imageViewToLauncherMap[imageView]?.launch(intent)
        }
    }
}
