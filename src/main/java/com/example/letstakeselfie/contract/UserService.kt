package com.example.letstakeselfie.contract

import com.example.letstakeselfie.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface UserService {
    fun login(email: String, password: String): Task<AuthResult>
    fun register(user: User): Task<AuthResult>
}