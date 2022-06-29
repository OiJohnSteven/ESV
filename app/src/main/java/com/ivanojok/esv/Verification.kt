package com.ivanojok.esv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ivanojok.esv.counsel.DoctorsHome
import com.ivanojok.esv.model.Counsellor
import com.ivanojok.esv.model.Police
import com.ivanojok.esv.model.PrefsManager
import com.ivanojok.esv.model.Victim
import com.ivanojok.esv.police.PoliceActivity
import java.util.concurrent.TimeUnit

class Verification : AppCompatActivity() {

 var mVerificationId: String ?= null

    private val prefsManager = PrefsManager.INSTANCE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

 prefsManager.setContext(this.application)

        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }


        val next = findViewById<Button>(R.id.verify)
        val code = findViewById<EditText>(R.id.code)

        val mail = intent.getStringExtra("email")

        val phone = intent.getStringExtra("phone")

        val tel = findViewById<TextView>(R.id.phone)
        tel.setText("We have sent a 6-digit code by sms to \\n $phone")


        sendVerificationCode(phone!!)

        next.setOnClickListener {
            val otp = code.text.toString()
            if (otp.isEmpty() || otp.length < 6) {
                return@setOnClickListener
            }
            //dialog!!.show()
            verifyVerificationCode(otp)
        }
    }

    fun sendVerificationCode(mobile: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mobile,
            60,
            TimeUnit.SECONDS,
            this,
            mCallbacks
        )
    }

    //the callback to detect the verification status
    private val mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken,
            ) {
                // super.onCodeSent(s, forceResendingToken)

                //storing the verification id that is sent to the user
                mVerificationId = s
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

                //Getting the code sent by SMS
                val code = phoneAuthCredential.smsCode


                //Log.d("code", code!!)
                //sometime the code is not detected automatically
                //in this case the code will be null
                //so user has to manually enter the code
                if (code != null) {
                    //code = firstPinView.text
                    //verifying the code
                    verifyVerificationCode(code)
                }

            }

            override fun onVerificationFailed(e: FirebaseException) {

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(this@Verification, "Wrong Phone number format", Toast.LENGTH_LONG).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(this@Verification, "Quota reached", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this@Verification, e.message, Toast.LENGTH_LONG).show()
                }

                startActivity(Intent(this@Verification, Login::class.java))
                finish()
            }

        }

    fun verifyVerificationCode(code: String) {
        //creating the credential
        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, code)

        //signing the user
        signInWithPhoneAuthCredential(credential)
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this@Verification,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {

                        val phoneNumber = intent.getStringExtra("phone")

                        val db = FirebaseDatabase.getInstance()
                        val ref = db.getReference("/users").child("victims").child(phoneNumber!!).addValueEventListener(
                            object : ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()){
                                        val user = snapshot.getValue(Victim::class.java)
                                        prefsManager.onLogin(user!!)
                                        startActivity(Intent(this@Verification, Services::class.java))
                                        finish()
                                    }
                                    else{

                                        db.getReference("/users").child("counsellors").child(phoneNumber).addValueEventListener(
                                            object : ValueEventListener{
                                                override fun onDataChange(snapshot: DataSnapshot) {
                                                    if (snapshot.exists()){
                                                        val user = snapshot.getValue(Counsellor::class.java)
                                                        prefsManager.onCounsellorLogin(user!!)
                                                        startActivity(Intent(this@Verification, DoctorsHome::class.java))
                                                        finish()
                                                    }
                                                    else{

                                                        db.getReference("/users").child("police").child(phoneNumber).addValueEventListener(
                                                            object : ValueEventListener {
                                                                override fun onDataChange(snapshot: DataSnapshot) {
                                                                    if (snapshot.exists()) {
                                                                        val user =
                                                                            snapshot.getValue(
                                                                                Police::class.java
                                                                            )
                                                                        prefsManager.onPoliceLogin(user!!)
                                                                        startActivity(
                                                                            Intent(
                                                                                this@Verification,
                                                                                PoliceActivity::class.java
                                                                            )
                                                                        )
                                                                        finish()
                                                                    }
                                                                    else{
                                                                        val intent = Intent(this@Verification, Register::class.java)
                                                                        intent.putExtra("phone", phoneNumber)
                                                                        startActivity(intent)
                                                                        finish()
                                                                    }
                                                                }

                                                                override fun onCancelled(error: DatabaseError) {

                                                                }
                                                            })
                                                    }
                                                }

                                                override fun onCancelled(error: DatabaseError) {

                                                }

                                            }
                                        )

                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {

                                }

                            }
                        )

                    } else {
                        Toast.makeText(this@Verification, task.exception.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                })
   


    }
}