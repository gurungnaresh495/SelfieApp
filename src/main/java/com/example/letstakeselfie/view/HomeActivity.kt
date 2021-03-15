package com.example.letstakeselfie.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.letstakeselfie.R
import com.example.letstakeselfie.adapter.ImagesRecyclerView
import com.example.letstakeselfie.app.Config
import com.example.letstakeselfie.contract.GalleryPresenter
import com.example.letstakeselfie.contract.GalleryView
import com.example.letstakeselfie.presenter.GalleryPresenterImpl
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_home.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.net.URI
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity(), View.OnClickListener, GalleryView {
    private var count = 0
    private lateinit var galleryPresenter: GalleryPresenter
    lateinit var auth : FirebaseAuth
    private var linkList = ArrayList<String>()
    private lateinit var storage :FirebaseStorage
    private lateinit var storageRef :StorageReference
    private lateinit var listAdapter: ImagesRecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    override fun onResume() {
        super.onResume()
        getImagesLink()
    }

    private fun init() {
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference
        galleryPresenter = GalleryPresenterImpl(this)

        auth = FirebaseAuth.getInstance()
        listAdapter = ImagesRecyclerView(this)
        images_recycler_view.adapter = listAdapter
        images_recycler_view.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        floating_button.setOnClickListener(this)
        camera_button.setOnClickListener(this)
        gallery_button.setOnClickListener(this)
        floating_button_refresh.setOnClickListener(this)
        floating_button_logout.setOnClickListener(this)
    }

    private fun getImagesLink() {
        linkList.clear()
        val listRef = storage.reference.child(auth.currentUser.email + "/")
        var tempList = listRef.listAll()
        tempList.addOnSuccessListener { it ->
            for (i in it.items)
            {
                i.downloadUrl.addOnSuccessListener {
                    linkList.add(it.toString())
                }.addOnFailureListener {
                    Log.d("abc", it.message.toString())
                }
            }
            listAdapter.updateList(linkList)
        }.addOnFailureListener {
            Log.d("abc", it.message.toString())
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            floating_button -> {
                camera_gallery_button_layout.visibility = View.VISIBLE
                floating_button.visibility = View.GONE
            }
            camera_button -> {
                galleryPresenter.askPermissions()
            }
            floating_button_refresh -> {
                getImagesLink()
            }
            floating_button_logout ->{

                auth.signOut()
                if (auth.currentUser == null)
                    finish()
            }
            gallery_button ->{
                chooseImage()
            }
        }
    }

    private fun chooseImage() {
        var i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(i, Config.GALLERY_CODE)
    }

    override fun displayImage() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Config.CAMERA_CODE && resultCode == Activity.RESULT_OK) {
            var outputStream: FileOutputStream
            var image = data?.extras?.get("data") as Bitmap

            var filepath: File = Environment.getExternalStorageDirectory()
            var dir = File(filepath.absolutePath + "/Demo/")
            dir.mkdir();
            var file = File(dir, System.currentTimeMillis().toString() + ".jpg")
            try {
                outputStream = FileOutputStream(file)
                image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
                var uri = Uri.fromFile(file)
                uploadImageToCloud(uri)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }

        else if(requestCode == Config.GALLERY_CODE && resultCode ==Activity.RESULT_OK)
        {
            var uri = data?.data
            uploadImageToCloud(uri)
        }
    }

    private fun uploadImageToCloud(uri: Uri?) {

        val ref = storageRef.child(auth.currentUser.email+ "/" + UUID.randomUUID()+ "-images.jpg")
        var uploadTask = ref.putFile(uri!!)
        uploadTask.addOnSuccessListener {
            Toast.makeText(this, "Upload Successful", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Upload Failed!", Toast.LENGTH_LONG).show()
        }
    }

    override fun openCamera() {
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, Config.CAMERA_CODE)
    }
}