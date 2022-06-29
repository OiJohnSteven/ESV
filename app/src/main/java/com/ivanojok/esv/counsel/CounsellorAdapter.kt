package com.ivanojok.esv.counsel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ivanojok.esv.ChatMessageActivity
import com.ivanojok.esv.R
import com.ivanojok.esv.model.Counsellor

class CounsellorAdapter(r:Context, options: ArrayList<Counsellor>) : RecyclerView.Adapter<CounsellorAdapter.CounsellorViewHolder>(){
    var c=r
    var y = options
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CounsellorViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.doctors_list, parent, false)
        return CounsellorViewHolder(inflate)
    }

    class CounsellorViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(context: Context,image:String, name:String){

            val i = itemView.findViewById<ImageView>(R.id.imageView3)

            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.account)
                .error(R.drawable.account)

            Glide.with(context).load(image).apply(options).into(i)
            val n = itemView.findViewById<TextView>(R.id.name)
            n.text = name
        }

    }

    override fun onBindViewHolder(holder: CounsellorViewHolder, position: Int) {
        Log.d("y", "$y")
        Log.d("c", "$c")
        holder.onBind(c, y[position].image!!, y[position].name!!)

        holder.itemView.setOnClickListener {
            val intent = Intent(c, ChatMessageActivity::class.java)
            intent.putExtra("name", y[position].name)
            intent.putExtra("dPhone", y[position].phoneNo)
            intent.putExtra("img", y[position].image)
            intent.putExtra("lat", y[position].latitude)
            intent.putExtra("long", y[position].longitude)
            intent.putExtra("nin", y[position].nin)
            c.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return y.size
    }
}