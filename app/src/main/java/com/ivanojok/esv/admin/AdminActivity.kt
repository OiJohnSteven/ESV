package com.ivanojok.esv.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.ivanojok.esv.R
import com.ivanojok.esv.police.ViewCases

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

                val c = findViewById<CardView>(R.id.cardView)
        c.setOnClickListener {
            startActivity(Intent(this, UsersActivity::class.java))

        }

        val d = findViewById<CardView>(R.id.cardView2)
        d.setOnClickListener {
            startActivity(Intent(this, CounsellorsActivity::class.java))
            finish()
        }

        val doctors = findViewById<CardView>(R.id.doctors)
        doctors.setOnClickListener {
            startActivity(Intent(this, AdminPoliceActivity::class.java))

        }


        val account = findViewById<CardView>(R.id.account)
        account.setOnClickListener {
            startActivity(Intent(this, ViewCases::class.java))

        }
    }
}