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

    override fun createEmail(email: String) {

        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if (!isEmailValid){
            view?.displayEmailFailure(R.string.invalid_email)
        }else{
            view?.displayEmailFailure(null)
        }

        if (isEmailValid){
            repository.createEmail(email, object : RegisterCallback {
                override fun onSuccess() {
                    view?.goToNameAndPasswordScreen(email)
                }
                override fun onFailure(message: String) {
                    // Se o email já estiver cadastrado, utiliza o resource de string para exibir o erro
                    if (message.equals("Usuário já cadastrado", ignoreCase = true)) {
                        view?.displayEmailFailure(R.string.email_ja_cadastrado)
                    } else {
                        view?.onEmailFailure(message)
                    }
                }
                override fun onComplete() {
                    view?.showProgress(false) // Corrigido para falso
                }
            })
        }
    }

    override fun onDestroy() {
        view = null
    }
}