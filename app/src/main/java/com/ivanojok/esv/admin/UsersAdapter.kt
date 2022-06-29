package com.ivanojok.esv.admin

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ivanojok.esv.ChatMessageActivity
import com.ivanojok.esv.R

class UsersAdapter(r: Context, options: ArrayList<String>) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {
    var c = r
    var y = options
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.doctors_list, parent, false)
        return UserViewHolder(inflate)
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(context: Context, name: String) {

            val n = itemView.findViewById<TextView>(R.id.name)
            n.text = name

//            val d = itemView.findViewById<TextView>(R.id.date)
//            d.text = date


        }

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        Log.d("y", "$y")
        Log.d("c", "$c")
        holder.onBind(c, y[position])

//        holder.itemView.setOnClickListener {
//            val intent = Intent(c, ChatMessageActivity::class.java)
//            intent.putExtra("name", y[position])
//            c.startActivity(intent)
//        }

    }

    override fun getItemCount(): Int {
        return y.size
    }
}