package com.ivanojok.esv.police

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.ivanojok.esv.MyAccount
import com.ivanojok.esv.R

class PoliceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_police)

        val d = findViewById<CardView>(R.id.cardView2)
        d.setOnClickListener {
            startActivity(Intent(this, ViewCases::class.java))
            finish()
        }

        val account = findViewById<CardView>(R.id.account)
        account.setOnClickListener {
            startActivity(Intent(this, MyAccount::class.java))

        }

    }
}