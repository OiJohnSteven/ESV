package com.ivanojok.esv.model

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

class VictimChatAdapter(r: Context, options: ArrayList<String>) : RecyclerView.Adapter<VictimChatAdapter.VictimViewHolder>() {
    var c = r
    var y = options
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VictimViewHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.doctors_list, parent, false)
        return VictimViewHolder(inflate)
    }

    class VictimViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(context: Context, name: String) {

            val n = itemView.findViewById<TextView>(R.id.name)
            n.text = name

//            val d = itemView.findViewById<TextView>(R.id.date)
//            d.text = date


        }

    }

    override fun onBindViewHolder(holder: VictimViewHolder, position: Int) {
        Log.d("y", "$y")
        Log.d("c", "$c")
        holder.onBind(c, y[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(c, ChatMessageActivity::class.java)
            intent.putExtra("dPhone", y[position])
            c.startActivity(intent)
        }

//        holder.itemView.setOnClickListener {
//            val intent = Intent(c, CreateReservation::class.java)
//            intent.putExtra("dId", y[position].id )
//            intent.putExtra("dPhone", y[position].phone)
//            c.startActivity(intent)
//        }

    }

    override fun getItemCount(): Int {
        return y.size
    }
}