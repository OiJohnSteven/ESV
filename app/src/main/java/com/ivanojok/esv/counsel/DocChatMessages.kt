package com.ivanojok.esv.counsel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ivanojok.esv.R
import com.ivanojok.esv.model.Chat
import com.ivanojok.esv.model.ChatAdapter
import com.ivanojok.esv.model.PrefsManager
import java.util.*

class DocChatMessages : AppCompatActivity() {
    private val prefsManager = PrefsManager.INSTANCE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doc_chat_messages)

        prefsManager.setContext(this.application)

        val name = intent.getStringExtra("name")
        val dName = findViewById<TextView>(R.id.dName)
        dName.text = name

        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            startActivity(Intent(this, DoctorChats::class.java))
            finish()
        }

        val uid = prefsManager.getCounsellor().phoneNo

        val rView = findViewById<RecyclerView>(R.id.chats)
        rView.layoutManager = LinearLayoutManager(this)


        val p = ArrayList<Chat>()
        val dbRef = FirebaseDatabase.getInstance().getReference("/chats").child(uid!!).child(name!!).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (y in snapshot.children){
                    Log.d("y", "$y")
                    val z = y.getValue(Chat::class.java)
                    Log.d("z", "${z!!.message}")
                    p.add(z)
                    Log.d("p", "$p")
                }
                val adapter = ChatAdapter(this@DocChatMessages, p)
                rView.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DocChatMessages, "An $error occured", Toast.LENGTH_LONG).show()
            }
        })



        val message = findViewById<EditText>(R.id.message)
        val send = findViewById<ImageView>(R.id.send)

        if (message.text.toString() == ""){
            send.setBackgroundColor(resources.getColor(R.color.white))
        }
        else{
            send.setBackgroundColor(resources.getColor(R.color.bg))
        }

        send.setOnClickListener {
            if (message.text.toString() == ""){
                return@setOnClickListener
            }
            else{

                val db = FirebaseDatabase.getInstance()
                //val id = UUID.randomUUID().toString()

                val time = Calendar.getInstance().time.toString()

                val ref = db.getReference("/chats").child(uid).child(name).push()
                val key = ref.key
                val chat = Chat(key, message.text.toString(), time, "Sent")
                val insert = ref.setValue(chat)

                val receiveRef =  db.getReference("/chats").child(name).child(uid).child(key!!)
                val rChat = Chat(key, message.text.toString(), time, "Received")
                val rInsert = receiveRef.setValue(rChat)

                if (insert.isCanceled || rInsert.isCanceled){
                    Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG).show()
                }
                else{
                    startActivity(getIntent())
                    finish()
                }

            }
        }

    }
}