package com.ivanojok.esv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.FirebaseApp
import com.ivanojok.esv.counsel.DoctorsHome
import com.ivanojok.esv.model.PrefsManager
import com.ivanojok.esv.police.PoliceActivity

class MainActivity : AppCompatActivity() {

private val prefsManager = PrefsManager.INSTANCE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

FirebaseApp.initializeApp(this)

        prefsManager.setContext(this.application)

        Handler().postDelayed({
            if (prefsManager.isLoggedIn()){
                //finish()
                startActivity(Intent(this, MapsFragment::class.java))
                finish()
            }
            else if(prefsManager.isCounsellorLoggedIn()){
                startActivity(Intent(this, DoctorsHome::class.java))
                finish()
            }
            else if (prefsManager.isPoliceLoggedIn()){
                startActivity(Intent(this, PoliceActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this, Login::class.java))
                finish()
            }
//
        }, 3000)
    }

}