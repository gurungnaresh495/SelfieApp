package com.example.letstakeselfie.presenter

import com.example.letstakeselfie.contract.LoginView
import com.example.letstakeselfie.contract.RegisterPresenter
import com.example.letstakeselfie.contract.RegisterView
import com.example.letstakeselfie.model.User
import com.example.letstakeselfie.model.UserServiceImpl

class RegisterPresenterImpl(var registerView: RegisterView): RegisterPresenter {
    private var userService = UserServiceImpl()
    override fun register(user: User) {
        var result = userService.register(user)
        result.addOnCompleteListener {
            registerView.onRegisterResult(it.isSuccessful)
        }

    }

}