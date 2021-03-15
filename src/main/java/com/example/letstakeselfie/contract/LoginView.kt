package com.example.letstakeselfie.contract

interface LoginView {

    fun setErrorMessage(visibility: Int)
    fun onLoginResult(result: Boolean)
    fun clearText()
}