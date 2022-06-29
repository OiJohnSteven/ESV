package com.ivanojok.esv.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.FirebaseDatabase
import com.ivanojok.esv.R
import com.ivanojok.esv.model.Police

class AddPoliceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_police)

        val back = findViewById<ImageView>(R.id.imageView2)
        back.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
            finish()
        }

        val add = findViewById<MaterialButton>(R.id.add)
        add.setOnClickListener {
            val f = findViewById<EditText>(R.id.first)
            val name = f.text.toString()

            val c = findViewById<EditText>(R.id.profession)
            val lat = c.text.toString()

            val doc = findViewById<EditText>(R.id.dNo)
            val long = doc.text.toString()

            val ph = findViewById<EditText>(R.id.phone)
            val phone = ph.text.toString()

            if (name.isNotEmpty() && lat.isNotEmpty() && long.isNotEmpty() && phone.isNotEmpty()){
                val police = Police(phone, name, lat, long)
               FirebaseDatabase.getInstance().getReference("/users").child("police").child(phone)
                   .setValue(police)
            }
            else{
                Toast.makeText(this, "fill in all details", Toast.LENGTH_SHORT).show()
            }


        }


    }
}