package com.example.letstakeselfie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.letstakeselfie.R
import com.example.letstakeselfie.contract.RegisterPresenter
import com.example.letstakeselfie.contract.RegisterView
import com.example.letstakeselfie.model.User
import com.example.letstakeselfie.presenter.RegisterPresenterImpl
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterView {
    private lateinit var registerPresenter: RegisterPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init() {
        registerPresenter = RegisterPresenterImpl(this)
        register_register_button.setOnClickListener {
            registerPresenter.register(User(register_edit_text_email.text.toString(),
                                        register_edit_text_password.text.toString()))
        }
    }

    override fun onRegisterResult(result: Boolean) {
        if (result)
        {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}