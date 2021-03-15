package com.example.letstakeselfie.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.letstakeselfie.R
import com.example.letstakeselfie.app.Config
import com.example.letstakeselfie.view.FullImageActivity
import com.example.letstakeselfie.view.HomeActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_row_layout.view.*

class ImagesRecyclerView(var context: Context) : RecyclerView.Adapter<ImagesRecyclerView.MyViewHolder>(){

    private var imageList = ArrayList<String>()

    inner class MyViewHolder(var view: View): RecyclerView.ViewHolder(view)
    {
        fun bind(url: String)
        {
            Glide.with(context).load(url).into(view.row_layout_image_view)

            view.setOnClickListener{
                var intent = Intent(context, FullImageActivity::class.java)
                intent.putExtra("Data", url)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.image_row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int {
        return this.imageList.size
    }
    fun updateList(arrayList: ArrayList<String>)
    {
        this.imageList = arrayList
        notifyDataSetChanged()
    }
}