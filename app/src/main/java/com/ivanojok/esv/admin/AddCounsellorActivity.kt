package com.ivanojok.esv.admin

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.ivanojok.esv.R
import com.ivanojok.esv.model.Counsellor

class AddCounsellorActivity : AppCompatActivity() {

    //private val prefsManager = PrefsManager.INSTANCE

    private val CAPTURE_PERMISSION_CODE = 1000
    // private val IMAGE_CAPTURE_CODE = 1001
    val PICK_PERMISSION_CODE = 1002
    val IMAGE_PICK_CODE = 1003

    var image_uri: Uri? = null
    lateinit var b: ImageView
    var mAuth: FirebaseAuth?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_counsellor)
        mAuth = FirebaseAuth.getInstance()

        val back = findViewById<ImageView>(R.id.imageView2)
        back.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
            finish()
        }

        val add = findViewById<MaterialButton>(R.id.add)
        add.setOnClickListener {
            val f = findViewById<EditText>(R.id.first)
            val first = f.text.toString()

            val l = findViewById<EditText>(R.id.last)
            val last = l.text.toString()

            val createEmail = findViewById<EditText>(R.id.createEmail)
            val nin = createEmail.text.toString()

            val c = findViewById<EditText>(R.id.profession)
            val lat = c.text.toString()

            val doc = findViewById<EditText>(R.id.dNo)
            val long = doc.text.toString()

            val ph = findViewById<EditText>(R.id.phone)
            val phone = ph.text.toString()

            if (nin.isNotEmpty() && first.isNotEmpty() && last.isNotEmpty() && lat.isNotEmpty() && long.isNotEmpty() && phone.isNotEmpty() && image_uri != null){
                uploadImage(phone, first + last,nin, lat, long)
                Log.d("upload", "${uploadImage(phone, first + last,nin, lat, long)}")
            }
            else{
                Toast.makeText(this, "fill in all details", Toast.LENGTH_SHORT).show()
            }


        }


        b = findViewById(R.id.img)
        b.setOnClickListener {
            pickImageFromGallery()
        }
    }


    fun pickImageFromGallery(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions:Array<out String>, grantResults:IntArray) {
        //this method is called, when user presses Allow or Deny from Permission Request Popup
        when (requestCode) {
//            CAPTURE_PERMISSION_CODE -> {
//                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    openCamera()
//                } else {
//                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
//                }
//            }
            PICK_PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            image_uri = data!!.data
            //img.setImageURI(image_uri)

            val bitmap = MediaStore.Images.Media
                .getBitmap(
                    contentResolver,
                    image_uri
                )
            b.setImageBitmap(bitmap)
//            b.borderWidth = R.dimen._2sdp
//            b.borderColor = resources.getColor(R.color.backPurple)
        }
    }


    // UploadImage method
    fun uploadImage(
        phone: String,
        name: String,
        nin: String,
        lat: String,
        long: String,
    ) {
        if (image_uri != null && mAuth!!.currentUser != null) {
            Log.d("image", image_uri.toString())

            //// && mAuth!!.currentUser != null

            // Defining the child of storageReference
            val sReference = FirebaseStorage.getInstance().getReference().child("/images")
                .child(image_uri.toString())
            val q = sReference.putFile(image_uri!!)
            val utask = q.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception.let {
                        throw it!!
                    }
                } else {
                    val t = sReference.downloadUrl
                    dbStore(
                        phone,
                        name,
                        nin,
                        t.toString(),
                        lat,
                        long,
                    )
                    sReference.downloadUrl

                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val imgUrl = task.result.toString()
                        dbStore(
                            phone,
                            name,
                            nin,
                            imgUrl,
                            lat,
                            long,
                        )
                    } else {
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
            else{

                Log.d("image", image_uri.toString())

                Log.d("anonymous", "ddd")
                signInAnonymously(
                    phone,
                    name,
                    nin,
                    lat,
                    long,
                )

            }
        }

    private fun signInAnonymously( phone:String,
                                   name:String,
                                   nin:String,
                                   lat:String,
                                   long:String,
                                   ) {

        mAuth!!.signInAnonymously().addOnSuccessListener(this, OnSuccessListener<AuthResult?> {
            // Defining the child of storageReference
            val sReference = FirebaseStorage.getInstance().getReference().child("/images").child(image_uri.toString())
            val q = sReference.putFile(image_uri!!)

            val utask = q.continueWithTask{ task ->
                if (!task.isSuccessful){
                    task.exception.let {
                        throw it!!
                    }
                }
                else{
                    val t = sReference.downloadUrl
                    dbStore( phone,
                        name,
                        nin,
                        t.toString(),
                        lat,
                        long,
                        )
                }
                sReference.downloadUrl

            }.addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val imgUrl = task.result.toString()
                    dbStore( phone,
                        name,
                        nin,
                        imgUrl,
                        lat,
                        long,
                        )
                }
                else{
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                }
            }

        })
            .addOnFailureListener(this,
                OnFailureListener { exception ->
                    Log.e(
                        "STORage",
                        "signInAnonymously:FAILURE",
                        exception
                    )
                })

    }

    fun dbStore(phone:String,
                name:String,
                nin:String,
                imgUrl:String,
                lat:String,
                long:String, ){
        val db =
            FirebaseDatabase.getInstance().getReference("/users").child("counsellors").child(phone)
                .setValue(
                    Counsellor(
                        phone,
                        name,
                        nin,
                        imgUrl,
                        lat,
                        long,
                    )
                )
        if (db.isCanceled) {
            Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG)
                .show()
        } else {
            startActivity(Intent(this, CounsellorsActivity::class.java))
            finish()
        }
    }



}