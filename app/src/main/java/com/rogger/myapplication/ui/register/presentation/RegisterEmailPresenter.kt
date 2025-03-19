package com.rogger.myapplication.ui.register.presentation

import android.util.Patterns
import com.rogger.myapplication.R
import com.rogger.myapplication.ui.register.RegisterEmail
import com.rogger.myapplication.ui.register.data.RegisterCallback
import com.rogger.myapplication.ui.register.data.RegisterRepository

class RegisterEmailPresenter(
    private var view: RegisterEmail.View?,
    private val repository: RegisterRepository
): RegisterEmail.Presenter {

    override fun create(email: String) {

        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if (!isEmailValid){
            view?.displayEmailFailure(R.string.invalid_email)
        }else{
            view?.displayEmailFailure(null)
        }

        if (isEmailValid){
            view?.showProgress(true)

            repository.create(email, object : RegisterCallback {
                override fun onSuccess() {
                    view?.goToNameAndPasswordScreen(email)
                }
                override fun onFailure(message: String) {
                    view?.onEmailFalure(message)
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