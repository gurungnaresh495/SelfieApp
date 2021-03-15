package com.example.letstakeselfie.model

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.letstakeselfie.contract.UserService
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class UserServiceImpl: UserService {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun login(email: String, password: String) :Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    override fun register(user: User): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(user.email, user.password)


    }
}