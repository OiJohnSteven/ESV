package com.ivanojok.esv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ivanojok.esv.counsel.CounsellorAdapter
import com.ivanojok.esv.model.Counsellor

class SelectCounsellor : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_doctor)


        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            startActivity(Intent(this, Services::class.java))
        }

        val rView = findViewById<RecyclerView>(R.id.recyclerView)
        rView.layoutManager = LinearLayoutManager(this)
        //val o = FirebaseRecyclerOptions.Builder<Doctor>().setQuery(dbRef, Doctor::class.java).build()

        val p = ArrayList<Counsellor>()
        val dbRef = FirebaseDatabase.getInstance().getReference("/users").child("counsellors").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (y in snapshot.children){
                    Log.d("y", "$y")
                    val z = y.getValue(Counsellor::class.java)
                    p.add(z!!)
                    Log.d("p", "$p")
                }
                val adapter = CounsellorAdapter(this@SelectCounsellor, p)
                rView.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SelectCounsellor, "An $error occured", Toast.LENGTH_LONG).show()
            }

        })
    }
}