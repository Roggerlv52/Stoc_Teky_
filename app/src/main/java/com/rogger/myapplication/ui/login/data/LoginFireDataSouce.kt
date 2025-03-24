package com.rogger.myapplication.ui.login.data

import com.google.firebase.auth.FirebaseAuth
import com.rogger.myapplication.molds.UserAuth

class LoginFireDataSouce(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) : LoginDataSource {

    override fun login(email: String, password: String, callback: LoginCallback) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        val userAuth = UserAuth(uuid = user.uid, email = user.email ?: "", name = "", password = "")
                        callback.onSuccess(userAuth)
                    } else {
                        callback.onFailure("Erro ao obter dados do usuário")
                    }
                } else {
                    callback.onFailure("Usuário não encontrado ou senha incorreta")
                }
                callback.onComplete()
            }
            .addOnFailureListener { exception ->
                callback.onFailure("Erro de conexão: ${exception.message}")
            }
    }
}