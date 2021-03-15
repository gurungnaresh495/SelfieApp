package com.example.letstakeselfie.contract

import com.example.letstakeselfie.model.User

interface RegisterPresenter {
    fun register(user: User)
}