//Yugaank
package com.example.yappin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import database
import java.io.ByteArrayOutputStream

class AddItem: ComponentActivity() {

    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private lateinit var imageButton: ImageButton
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_activity)

        val radioGroup: RadioGroup = findViewById(R.id.radio_group)
        val spinner: Spinner = findViewById(R.id.spinner_category)

        R.array.upper_body_array
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val itemsArray = when (checkedId) {
                R.id.radio_upper -> R.array.upper_body_array
                R.id.radio_lower -> R.array.lower_body_array
                R.id.radio_other -> R.array.accessory_array
                else -> R.array.upper_body_array // default option
            }
            updateSpinner(itemsArray, spinner)
        }

        var imageBlob: ByteArray? = null
        imageButton = findViewById(R.id.imageButton)

        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Get the image from the data
                val imageUri: Uri? = result.data?.data
                imageUri?.let {
                    imageButton.setImageURI(it)

                    // Convert the selected image to a BLOB
                    val source = ImageDecoder.createSource(contentResolver, it)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    imageBlob = imageToByteArray(bitmap)

                }
            }
        }

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                imagePickerLauncher.launch(intent)
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        imageButton.setOnClickListener {
            handleImageSelection()
        }

        val g = database(this)
        //val db: SQLiteDatabase = g.readableDatabase

        val submitButton: Button = findViewById(R.id.button_submit)
        // Set an OnClickListener on the submit button
        submitButton.setOnClickListener {

            val radioID = findViewById<RadioGroup>(R.id.radio_group).checkedRadioButtonId
            val upper = findViewById<RadioButton>(radioID).text.toString()
            val selectedCategory = findViewById<Spinner>(R.id.spinner_category).selectedItem.toString()
            val color = findViewById<EditText>(R.id.input_color).text.toString()
            val material = findViewById<EditText>(R.id.input_material).text.toString()
            val isPersonal = findViewById<Switch>(R.id.switch2).isChecked

            val db = g.writableDatabase

            val values = ContentValues().apply {
                put("ownership", if (isPersonal) "Personal" else "Not Owned")
                put("color", color)
                put("material", material)
                put("upper_lower", upper)
                put("type", selectedCategory)
                put("image",imageBlob)
            }

            // Insert the new row, returning the primary key value of the new row
            val newRowId = db?.insert("Apparel", null, values)

            // Provide feedback to the user
            if (newRowId != -1L) {
                Toast.makeText(this, "Apparel added successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Log.e("DB_INSERT_ERROR", "Error inserting row with values: $values")

                Toast.makeText(this, "Error adding apparel", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun updateSpinner(itemsArray: Int, spinner: Spinner) {
        val adapter = ArrayAdapter.createFromResource(
            this,
            itemsArray,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun handleImageSelection() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(permission)
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }
    }

    private fun imageToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

}