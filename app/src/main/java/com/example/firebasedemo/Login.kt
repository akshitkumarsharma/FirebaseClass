package com.example.firebasedemo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    lateinit var mail:EditText
    lateinit var pass:EditText
    lateinit var fa:FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mail=findViewById(R.id.email)
        pass=findViewById(R.id.password)
        fa=FirebaseAuth.getInstance()

        val loginBT: Button =findViewById(R.id.loginBT)
        loginBT.setOnClickListener {
            fa.signInWithEmailAndPassword(mail.text.toString(), pass.text.toString()).addOnCompleteListener(this)
            { task->
                if(task.isSuccessful)
                {
                    startActivity(Intent(this, Welcome::class.java))
                }
                else
                {

                }
            }
        }



    }

}