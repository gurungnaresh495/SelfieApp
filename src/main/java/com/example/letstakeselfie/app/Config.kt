package com.example.letstakeselfie.app

class Config {
    companion object
    {
        const val CAMERA_CODE = 10001
        const val GALLERY_CODE = 10002
        const val imageUrl = "https://firebasestorage.googleapis.com/v0/b/lets-take-selfie.appspot.com/o/"
        const val token = "68ac6689-324c-4d7a-b78a-81395147fdd6"

        fun getImageUrl(email: String, filename: String): String
        {
            return "$imageUrl$email%2F$filename?alt=media&token=$token"
        }
    }
}