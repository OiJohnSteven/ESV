package com.ivanojok.esv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ivanojok.esv.counsel.DoctorChats

class Services : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)


        val d = findViewById<CardView>(R.id.cardView2)
        val account = findViewById<CardView>(R.id.account)
        val c = findViewById<CardView>(R.id.cardView)
        c.setOnClickListener {
            startActivity(Intent(this, MapsFragment::class.java))

        }


        d.setOnClickListener {
            startActivity(Intent(this, ConsultingCounsellor::class.java))

        }


        account.setOnClickListener {
            startActivity(Intent(this, MyAccount::class.java))

        }


    }
}