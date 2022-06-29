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
import com.ivanojok.esv.model.Counsellor

class CounsellorsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counsellors)

        val add = findViewById<ImageView>(R.id.add)
        add.setOnClickListener{
            startActivity(Intent(this, AddCounsellorActivity::class.java))
        }

        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener{
            startActivity(Intent(this, AdminActivity::class.java))
        }


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val v = ArrayList<String>()
        val ref = FirebaseDatabase.getInstance().getReference("/users").child("counsellors")
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {

                            for (i in snapshot.children){
                                val z = i.getValue(Counsellor::class.java)
                                v.add(z!!.name!!)
                            }

                            recyclerView.adapter = UsersAdapter(this@CounsellorsActivity, v)
                            recyclerView.layoutManager = LinearLayoutManager(this@CounsellorsActivity)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

    }
}