package com.ivanojok.esv.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ivanojok.esv.R
import com.ivanojok.esv.model.Police

class AdminPoliceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_police)

        val add = findViewById<ImageView>(R.id.add)
        add.setOnClickListener{
            startActivity(Intent(this, AddPoliceActivity::class.java))
        }

        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener{
            startActivity(Intent(this, AdminActivity::class.java))
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val v = ArrayList<String>()
        val ref = FirebaseDatabase.getInstance().getReference("/users").child("police")
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {

                            for (i in snapshot.children){
                                val z = i.getValue(Police::class.java)
                                v.add(z!!.name!!)
                            }

                            recyclerView.adapter = UsersAdapter(this@AdminPoliceActivity, v)
                            recyclerView.layoutManager = LinearLayoutManager(this@AdminPoliceActivity)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })


    }
}