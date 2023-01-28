package com.example.firebasedemo

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*

class MyTeam : AppCompatActivity() {

    lateinit var et1: EditText
    lateinit var et2: EditText

    // views for button
    lateinit var btnSelect: Button  // views for button
    lateinit var btnUpload: Button

    // view for image view
    lateinit var imageView: ImageView

    // Uri indicates, where the image will be picked from
    lateinit var filePath: Uri

    // request code
    val PICK_IMAGE_REQUEST = 22

    // instance for firebase storage and StorageReference
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null

    lateinit var my: DatabaseReference
    var mFirebaseDatabase: FirebaseDatabase?=null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_team)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        my = FirebaseDatabase.getInstance().reference //.child("Faculty");
        et1 = findViewById(R.id.enterid)
        et2 = findViewById(R.id.enterphnum)


        // initialise views
        btnSelect = findViewById(R.id.btnChoose)
        btnUpload = findViewById(R.id.btnUpload)
        imageView = findViewById(R.id.imgView)

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.getReference()

        val Save:Button=findViewById(R.id.Save)
        Save!!.setOnClickListener(){
            val s=et1.text.toString()
            name=s.toString()
            val s2= et2.text.toString()
            ph=s2.toLong();
            section = et3.text.toString()
            var s1=et4.text.toString()
            index=s1.toFloat()
            f = Team(id,ph)
// my.child("userfaculty").setValue(f);
            my!!.child("faculty").child("id").setValue(id);
            my!!.child("faculty").child("ph").setValue(ph);

        }



        // on pressing btnSelect SelectImage() is called
        btnSelect.setOnClickListener {
            SelectImage()
        }

        // on pressing btnUpload uploadImage() is called
        btnUpload.setOnClickListener {
            uploadImage()
        }
    }

    // Select Image method
    private fun SelectImage() {

        // Defining Implicit Intent to mobile gallery
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Select Image from here..."
            ),
            PICK_IMAGE_REQUEST
        )
    }

    // Override onActivityResult method
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {

            // Get the Uri of data
            filePath = data.data!!
            try {

                // Setting image on image view using Bitmap
                val bitmap = MediaStore.Images.Media
                    .getBitmap(
                        contentResolver,
                        filePath
                    )
                imageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                // Log the exception
                e.printStackTrace()
            }
        }
    }

    // UploadImage method
    private fun uploadImage() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            // Defining the child of storageReference
            val ref = storageReference
                ?.child(
                    "images/"
                            + UUID.randomUUID().toString()
                )

            // adding listeners on upload
            // or failure of image
            if (ref != null) {
                ref.putFile(filePath)
                    .addOnSuccessListener { // Image uploaded successfully
                        // Dismiss dialog
                        progressDialog.dismiss()
                        Toast
                            .makeText(
                                this,
                                "Image Uploaded!!",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                    .addOnFailureListener { e -> // Error, Image not uploaded
                        progressDialog.dismiss()
                        Toast
                            .makeText(
                                this,
                                "Failed " + e.message,
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                    .addOnProgressListener { taskSnapshot ->

                        // Progress Listener for loading
                        // percentage on the dialog box
                        val progress = (100.0
                                * taskSnapshot.bytesTransferred
                                / taskSnapshot.totalByteCount)
                        progressDialog.setMessage(
                            "Uploaded "
                                    + progress.toInt() + "%"
                        )
                    }
            }
        }
    }
}