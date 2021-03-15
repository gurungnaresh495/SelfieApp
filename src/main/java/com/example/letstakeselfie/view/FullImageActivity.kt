package com.example.letstakeselfie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.example.letstakeselfie.R
import kotlinx.android.synthetic.main.activity_full_image.*
import kotlinx.android.synthetic.main.image_row_layout.view.*

class FullImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)
        init()
    }

    private fun init() {
        var url = intent.getStringExtra("Data")
        Glide.with(this).load(url).into(full_image_image_view)
    }
}