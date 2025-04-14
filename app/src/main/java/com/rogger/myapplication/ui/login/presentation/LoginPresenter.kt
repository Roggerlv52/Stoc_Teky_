package com.rogger.myapplication.ui.login.presentation

import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.rogger.myapplication.R
import com.rogger.myapplication.molds.UserAuth
import com.rogger.myapplication.ui.login.Login
import com.rogger.myapplication.ui.login.data.LoginCallback
import com.rogger.myapplication.ui.login.data.LoginRepository
import com.rogger.myapplication.ui.splashScreen.data.SplashLocalDataSource

class LoginPresenter(
    private var view: Login.View?,
    private var repository: LoginRepository,
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
            view?.showProgress(true)
            repository.logon(email, password, object : LoginCallback {

                override fun onSuccess(userAuth: UserAuth) {
                    view?.onUserAuthenticated()
                    view?.showProgress(false)
                    view?.onUserAuthenticated()
                }

                override fun onFailure(message: String) {
                    view?.onUserUnauthorized(message)
                    view?.showProgress(false)
                    view?.displayEmailFailure(R.string.user_not_faund)

                }

                override fun onFailurePassword(message: String) {
                    view?.showProgress(false)
                    view?.displayPasswordFailure(R.string.password_error)
                }

                override fun onComplete() {
                    view?.showProgress(true)
                }
            })
        } else {
            view?.showProgress(false)
        }
    }
    override fun recoverPassword(email: String) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if (!isEmailValid) {
            view?.displayEmailFailure(R.string.invalid_email)
            return
        }

        view?.displayEmailFailure(null)
        view?.showProgress(true)

        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val signInMethods = task.result?.signInMethods ?: emptyList()
                    if (signInMethods.isEmpty()) {
                        // Email não registrado
                       // view?.displayEmailFailure(R.string.user_not_faund)
                        sendPasswordReset(email)
                        view?.showProgress(false)
                    } else {
                        // Enviar email normalmente
                        //sendPasswordReset(email)
                        view?.displayEmailFailure(R.string.user_not_faund)
                    }
                } else {
                    view?.displayEmailFailure(R.string.password_error)
                    view?.showProgress(false)
                }
            }
    }

    private fun sendPasswordReset(email: String) {
        // Obtém a instância do Firestore
        val firestore = FirebaseFirestore.getInstance()

        // Consulta a coleção "users" onde o campo "email" seja igual ao email fornecido
        firestore.collection("/users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    view?.displayEmailFailure(R.string.email_not_found)
                } else {
                    // Envia o e-mail de reset
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener { resetTask ->
                            view?.showProgress(false)
                            if (resetTask.isSuccessful) {
                                view?.onUserAuthenticated()
                            } else {
                                view?.displayEmailFailure(R.string.error_in)
                            }
                        }
                }

            }
    }

    override fun onDestroy() {
        view = null
    }

}


