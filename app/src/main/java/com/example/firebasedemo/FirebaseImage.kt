package com.example.firebasedemo

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.util.*


class FirebaseImage : AppCompatActivity() {

    val pic_id=123
    var fs:FirebaseStorage?=null
    var sr:StorageReference?=null
    lateinit var filepath:Uri
    lateinit var clickImage:Button
    lateinit var uploadImage:Button
    lateinit var iv:ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_image)

        clickImage=findViewById(R.id.chooseImgBT)
        uploadImage=findViewById(R.id.uploadImgBT)
        iv=findViewById(R.id.image_preview)

        fs= FirebaseStorage.getInstance()
        //sr=FirebaseStorage.getInstance().reference
        sr= fs!!.reference

        clickImage.setOnClickListener {
            val camera_intent=if(Build.VERSION.SDK_INT > Build.VERSION_CODES.CUPCAKE)
            {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            }
            else
            {

            }
            startActivityForResult(camera_intent as Intent?, pic_id)

        }

        uploadImage.setOnClickListener {
            uploadImage()
        }


    }


    private fun uploadImage()
    {
        iv.buildDrawingCache()
        var b:Bitmap=iv.getDrawingCache()
        var ba= ByteArrayOutputStream()
        b.compress(Bitmap.CompressFormat.PNG, 100, ba)

        var byteArray=ba.toByteArray()
        val path="mystoragedemo/" + UUID.randomUUID().toString()+".png"
        sr=fs!!.getReference(path)
        sr!!.putBytes(byteArray)
        uploadImage.setEnabled(false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==pic_id && resultCode == RESULT_OK)
        {
            filepath=data!!.data!!
            iv.setImageURI(filepath)
        }
    }
}