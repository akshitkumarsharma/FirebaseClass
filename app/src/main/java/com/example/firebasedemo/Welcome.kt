package com.example.firebasedemo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Welcome : AppCompatActivity() {

    lateinit var email:TextView
    lateinit var pass:TextView
    lateinit var db:FirebaseDatabase
    lateinit var dr:DatabaseReference
    lateinit var uId:String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        email=findViewById(R.id.email)
        pass=findViewById(R.id.password)
        db=FirebaseDatabase.getInstance()
        dr=db!!.getReference("users")

        val user=FirebaseAuth.getInstance().currentUser
        if(user!=null)
        {
            uId=user.uid
        }

        val show:Button=findViewById(R.id.showBT)

        show.setOnClickListener {
            dr!!.child(uId!!).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user=snapshot.getValue(User::class.java)
                    /*if(user==null)
                    {
                        return

                    }*/
                    email.text=user!!.email
                    pass.text=user!!.pass
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}