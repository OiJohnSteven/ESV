package com.ivanojok.esv.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ivanojok.esv.R
import com.ivanojok.esv.model.Victim

class UsersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val v = ArrayList<String>()
        val ref = FirebaseDatabase.getInstance().getReference("/users").child("victim")
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {

                            for (i in snapshot.children){
                                val z = i.getValue(Victim::class.java)
                                v.add("${z!!.firstName} ${z.lastName}")
                            }

                            recyclerView.adapter = UsersAdapter(this@UsersActivity, v)
                            recyclerView.layoutManager = LinearLayoutManager(this@UsersActivity)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

    }
}