package com.example.firebasedemo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var fd:FirebaseDatabase
    lateinit var df:DatabaseReference
    lateinit var userId:String
    lateinit var mail:String
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email: TextView =findViewById(R.id.email)
        val password: TextView =findViewById(R.id.password)
        val signBT: Button =findViewById(R.id.signBT)
        val sendLinkBT: Button=findViewById(R.id.sendLinkBT)

        var fb=FirebaseAuth.getInstance()
        fd=FirebaseDatabase.getInstance()

        /*fun signInMethod(view: View)
        {
            fb.createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener(this)
            {task->
                Toast.makeText(this, "Created"+task.isSuccessful,Toast.LENGTH_SHORT).show()
            }
        }*/

        signBT.setOnClickListener {
            fb.createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener(this)
            {task->
                Toast.makeText(this, "Created "+task.isSuccessful,Toast.LENGTH_SHORT).show()
                if(task.isSuccessful)
                {
                    df=fd.getReference("users")
                    val user=FirebaseAuth.getInstance().currentUser
                    userId=user!!.uid

                    val myuser=User(email.text.toString(), password.text.toString())

                    df.child(userId).setValue(myuser)
                }
                else
                {
                    Toast.makeText(this,task.exception!!.message,Toast.LENGTH_SHORT).show()
                }
            }
        }

        sendLinkBT.setOnClickListener {
            fb.createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener(this)
            {task->
                if(task.isSuccessful)
                {
                    fb.currentUser?.sendEmailVerification()!!.addOnCompleteListener(this)
                    {
                        if(task.isSuccessful)
                        {
                            Toast.makeText(this,"Link send "+task.isSuccessful, Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            Toast.makeText(this,task.exception!!.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else
                {
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}