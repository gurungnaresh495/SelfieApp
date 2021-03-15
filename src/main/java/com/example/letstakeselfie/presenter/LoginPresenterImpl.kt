package com.example.letstakeselfie.presenter

import android.view.View
import com.example.letstakeselfie.contract.LoginPresenter
import com.example.letstakeselfie.contract.LoginView
import com.example.letstakeselfie.model.UserServiceImpl

class LoginPresenterImpl(var loginView: LoginView) : LoginPresenter {

    private var userServiceImpl =  UserServiceImpl()
    override fun login(email: String, password: String) {
        var result =userServiceImpl.login(email, password)
        result.addOnCompleteListener {
            loginView.onLoginResult(it.isSuccessful)
            if ( !it.isSuccessful )
                loginView.setErrorMessage(View.VISIBLE)
        }


    }
}