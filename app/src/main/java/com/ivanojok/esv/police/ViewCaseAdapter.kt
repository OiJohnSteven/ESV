package com.ivanojok.esv.police

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
import com.ivanojok.esv.R
import com.ivanojok.esv.model.Case

class ViewCaseAdapter(r: Context, options: ArrayList<Case>) : RecyclerView.Adapter<ViewCaseAdapter.ViewCaseViewHolder>(){
    var c=r
    var y = options
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewCaseViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.doctors_list, parent, false)
        return ViewCaseViewHolder(inflate)
    }

    class ViewCaseViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(context: Context, name:String){

            val n = itemView.findViewById<TextView>(R.id.name)
            n.text = name
        }

    }

    override fun onBindViewHolder(holder: ViewCaseViewHolder, position: Int) {
        Log.d("y", "$y")
        Log.d("c", "$c")
        holder.onBind(c, y[position].caseId!!)

    }

    override fun getItemCount(): Int {
        return y.size
    }
}