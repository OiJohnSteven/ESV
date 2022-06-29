package com.ivanojok.esv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ivanojok.esv.model.PrefsManager

class MyAccount : AppCompatActivity() {

    private val prefsManager = PrefsManager.INSTANCE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)
        prefsManager.setContext(this.application)

        val name = findViewById<TextView>(R.id.name)
        val phone = findViewById<TextView>(R.id.id)
        val nin = findViewById<TextView>(R.id.course)
        val residence = findViewById<TextView>(R.id.email)
        val imageView7 = findViewById<ImageView>(R.id.imageView7)


        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.account)
            .error(R.drawable.account)


        if (prefsManager.isLoggedIn()) {
            val victim = prefsManager.getVictim()

            name.text = "${victim.firstName}  ${victim.lastName}"
            phone.text = "${victim.phone}"
            nin.text = "NIN: ${victim.nin}"
            residence.text = "Residence: ${victim.residence}"

        }
        else if(prefsManager.isCounsellorLoggedIn()){
            val counsellor = prefsManager.getCounsellor()
            name.text = counsellor.name
            phone.text = counsellor.phoneNo
            nin.text = "NIN: ${counsellor.nin}"
            residence.text = "Latitude: ${counsellor.latitude} \n Logitude: ${counsellor.longitude}"

            Glide.with(this).load(prefsManager.getCounsellor().image).apply(options).into(imageView7)
        }
        else if(prefsManager.isPoliceLoggedIn()){
            val police = prefsManager.getPolice()
            name.text = police.name
            phone.text = police.phoneNo
            nin.text = "Latitude: ${police.latitude} \n Logitude: ${police.longitude}"
            residence.visibility = View.INVISIBLE

        }



        val logout = findViewById<Button>(R.id.logout)
        logout.setOnClickListener {

            if(prefsManager.isLoggedIn()) {
                prefsManager.onVictimLogout()
            }
            else if(prefsManager.isCounsellorLoggedIn()){
               prefsManager.onCounsellorLogout()
            }
            else if(prefsManager.isPoliceLoggedIn()){
                prefsManager.onPoliceLogout()
            }

            startActivity(Intent(this, Login::class.java))
            finish()
        }

    }
}