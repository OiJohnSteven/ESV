package com.ivanojok.esv.police

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
import com.ivanojok.esv.admin.AdminActivity
import com.ivanojok.esv.model.Case
import com.ivanojok.esv.model.PrefsManager

class ViewCases : AppCompatActivity() {
    private val prefsManager = PrefsManager.INSTANCE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_cases)

        prefsManager.setContext(this.application)
        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener{
            if (prefsManager.getPolice().phoneNo != null){
                startActivity(Intent(this, PoliceActivity::class.java))
            }
            else{
                startActivity(Intent(this, AdminActivity::class.java))
            }

        }


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val v = ArrayList<Case>()
        val ref = FirebaseDatabase.getInstance().getReference("/cases")
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {

                            for (i in snapshot.children){
                                val z = i.getValue(Case::class.java)
                                v.add(z!!)
                            }

                            recyclerView.adapter = ViewCaseAdapter(this@ViewCases, v)
                            recyclerView.layoutManager = LinearLayoutManager(this@ViewCases)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

    }
}