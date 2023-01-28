package com.example.firebasedemo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.*

class FirebaseListView : AppCompatActivity() {

    lateinit var et1: EditText
    lateinit var et2: EditText
    lateinit var et3: EditText
    lateinit var et4: EditText
    var id: Int = 0
    var ph: Long = 0
    lateinit var section: String
    var index = 0f
    lateinit var my: DatabaseReference
    var mFirebaseDatabase: FirebaseDatabase?=null
    //lateinit var Save: Button
    lateinit var f: Faculty
    lateinit var lv: ListView
    lateinit var al: ArrayList<String>
    lateinit var ad: ArrayAdapter<String>
    var count = 1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_list_view)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        my = FirebaseDatabase.getInstance().reference //.child("Faculty");
        et1 = findViewById(R.id.enterid)
        et2 = findViewById(R.id.enterphnum)
        et3 = findViewById(R.id.entersection)
        et4 = findViewById(R.id.enterindex)
        lv = findViewById(R.id.lv)

        al = ArrayList()
        ad = ArrayAdapter(this, android.R.layout.simple_list_item_1, al!!)
        lv.adapter=ad
        val Save:Button=findViewById(R.id.Save)
        Save!!.setOnClickListener(){
            val s=et1.text.toString()
            id=s.toInt()
            val s2= et2.text.toString()
            ph=s2.toLong();
            section = et3.text.toString()
            var s1=et4.text.toString()
            index=s1.toFloat()
            f = Faculty(id,ph,section,index)
// my.child("userfaculty").setValue(f);
            my!!.child("faculty").child("id").setValue(id);
            my!!.child("faculty").child("ph").setValue(ph);
            my!!.child("faculty").child("section").setValue(section);
            my!!.child("faculty").child("index").setValue(index);
/* f.setId(id);
f.setPhonenumber(ph);
f.setSection(section);
f.setParticipationIndex(index);
//myref.push().setValue(f);
// my.child("faculty")
my.child("Faculty").push().setValue(f).addOnCompleteListener(new OnCompleteListener<Void>() {
@Override
public void onComplete(@NonNull Task<Void> task) {

if (task.isSuccessful()) {

Save.setText("Saved");
Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
} else {

Save.setText("Not saved");
Toast.makeText(getApplicationContext(), "" + task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
}
}
});;
// Toast.makeText(getApplicationContext(),"done I guess",Toast.LENGTH_LONG).show();
}
});
*/
        }

        val readBT:Button=findViewById(R.id.readBT)
        readBT.setOnClickListener {

            my!!.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot != null) {
                        var s1: String=""


                        var it: Iterable<DataSnapshot>
                        it = snapshot!!.children
                        it.forEach()
                        {
                            Toast.makeText(
                                getApplicationContext(),
                                "" + it.getValue(), Toast.LENGTH_SHORT
                            ).show();
                            s1 = s1 + it.getValue();
                        }
                        al!!.add(s1)
    //al.add(s1);
                    }
                    /*val faculty=snapshot.getValue(Faculty::class.java)
                    var s1:String=""
                    s1=s1+faculty!!.id
                    s1=s1+faculty!!.ph
                    s1=s1+faculty!!.index
                    s1=s1+faculty!!.section

                    al!!.add(s1)*/

                    ad!!.notifyDataSetChanged();
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            });
        }

    }

    /*fun doRead(view: View)
    {
        my!!.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
                /*if (snapshot != null) {
                    var s1: String;


                    var it: Iterable<DataSnapshot>
                    it = snapshot!!.children
                    it.forEach()
                    {
                        Toast.makeText(
                            getApplicationContext(),
                            "" + it.getValue(), Toast.LENGTH_SHORT
                        ).show();
                        s1 = s1 + it.getValue();
                    }
                    al!!.add(s1)
//al.add(s1);
                }*/
                val faculty=snapshot.getValue(Faculty::class.java)
                val s1:String
                s1=s1+faculty!!.id
                s1=s1+faculty!!.ph
                s1=s1+faculty!!.index
                s1=s1+faculty!!.section

                al!!.add(s1)

                ad!!.notifyDataSetChanged();
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        });

    }*/
}