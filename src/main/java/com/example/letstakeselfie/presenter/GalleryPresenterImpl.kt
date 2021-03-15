package com.example.letstakeselfie.presenter

import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import com.example.letstakeselfie.MyApplication
import com.example.letstakeselfie.app.Config
import com.example.letstakeselfie.contract.GalleryPresenter
import com.example.letstakeselfie.contract.GalleryView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class GalleryPresenterImpl(var homeView: GalleryView) : GalleryPresenter {


    override fun askPermissions() {
        Dexter.withContext(MyApplication.appContext).withPermissions(android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
                        homeView.openCamera()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    TODO("Not yet implemented")
                }

            }).onSameThread().check()
    }
}