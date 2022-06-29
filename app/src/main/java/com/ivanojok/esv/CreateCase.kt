 package com.ivanojok.esv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase
import com.ivanojok.esv.model.Case
import com.ivanojok.esv.model.PrefsManager

 class CreateCase : AppCompatActivity() {

     private val prefsManager = PrefsManager.INSTANCE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_case)

        val Id = prefsManager.getVictim().phone

        val latitude = intent.getStringExtra("latitude")
        val longitude = intent.getStringExtra("longitude")


        val first = findViewById<EditText>(R.id.first)

        val add = findViewById<Button>(R.id.add)
        add.setOnClickListener {
            val comment = first.text.toString()
            if (comment.isNotEmpty()) {
            val ref = FirebaseDatabase.getInstance().getReference("/cases").push()
            val k = ref.key
            ref.setValue(Case(k!!, comment, latitude!!, longitude!!, Id!!))
        }
      }
    }
}