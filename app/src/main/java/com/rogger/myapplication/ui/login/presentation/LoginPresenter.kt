package com.rogger.myapplication.ui.login.presentation

import android.util.Log
import android.util.Patterns
import com.rogger.myapplication.R
import com.rogger.myapplication.molds.UserAuth
import com.rogger.myapplication.ui.login.Login
import com.rogger.myapplication.ui.login.data.LoginCallback
import com.rogger.myapplication.ui.login.data.LoginRepository

class LoginPresenter(
    private var view: Login.View?,
    private var repository: LoginRepository
) : Login.Presenter {

    override fun login(email: String, password: String) {

        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.length >= 8

        if (!isEmailValid) {
            view?.displayEmailFailure(R.string.invalid_email)
        } else {
            view?.displayEmailFailure(null)
        }
        if (!isPasswordValid) {
            view?.displayPasswordFailure(R.string.invalid_password)
        } else {
            view?.displayPasswordFailure(null)
        }
        if (isEmailValid && isPasswordValid) {
            repository.logon(email, password, object : LoginCallback {
                override fun onSuccess(userAuth: UserAuth) {
                    view?.onUserAuthenticated()
                }

                override fun onFailure(message: String) {
                    view?.onUserUnauthoried(message)

                }

                override fun onComplete() {
                    view?.showProgress(true)
                }

            })
        }
    }

    override fun onDestroy() {
        view = null
    }

}