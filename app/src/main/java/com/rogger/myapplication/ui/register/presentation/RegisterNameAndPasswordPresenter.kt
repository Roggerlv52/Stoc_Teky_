package com.rogger.myapplication.ui.register.presentation

import com.rogger.myapplication.R
import com.rogger.myapplication.ui.register.data.RegisterCallback
import com.rogger.myapplication.ui.register.data.RegisterRepository
import com.rogger.myapplication.ui.register.RegisterNameAndPassword

class RegisterNameAndPasswordPresenter(
    private var view: RegisterNameAndPassword.View?,
    private val repository: RegisterRepository
) : RegisterNameAndPassword.Presenter {

    override fun createNameAndPassword(
        email: String,
        name: String,
        password: String,
        confirme: String
    ) {

        val isNameValid = name.length >= 3
        val isPasswordValid = password.length >= 8
        val isConfirmValid = password == confirme

        if (!isNameValid) {
            view?.displayNameFailure(R.string.invalid_name)
        } else {
            view?.displayNameFailure(null)
        }

        if (!isConfirmValid) {
            view?.displayPasswordFailure(R.string.password_not_equal)
        } else {
            if (!isPasswordValid) {
                view?.displayPasswordFailure(R.string.invalid_password)
            } else {
                view?.displayPasswordFailure(null)
            }
        }

        if (isNameValid && isPasswordValid && isConfirmValid) {
            view?.showProgress(true)

            repository.createNameAndPassword(email, name, password, object : RegisterCallback {
                override fun onSuccess() {
                    view?.onCreateSuccess(name,email)
                }

                override fun onFailure(message: String) {
                    view?.onCreateFailure(message)
                }

                override fun onComplete() {
                    view?.showProgress(false)
                }
            })
        }
    }

    override fun onDestroy() {
        view = null
    }
}