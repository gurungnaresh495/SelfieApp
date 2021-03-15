package com.example.letstakeselfie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.letstakeselfie.R
import com.example.letstakeselfie.presenter.LoginPresenterImpl
import com.example.letstakeselfie.contract.LoginPresenter
import com.example.letstakeselfie.contract.LoginView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LoginView, View.OnClickListener{
    private lateinit var loginPresenter: LoginPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        loginPresenter = LoginPresenterImpl(this)
        login_button_login.setOnClickListener(this)
        login_redirect_to_register.setOnClickListener(this)
    }

    override fun setErrorMessage(visibility: Int) {
        login_error_message.visibility = visibility
    }

    override fun onLoginResult(result: Boolean) {
        if (result)
            startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun clearText() {
        login_edit_text_email.setText("email")
        login_edit_text_password.setText("password")
    }

    override fun onClick(view: View?) {
        when(view){
            login_button_login -> loginPresenter.login(login_edit_text_email.text.toString(), login_edit_text_password.text.toString())
            login_redirect_to_register -> startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}