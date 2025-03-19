package com.rogger.myapplication.ui.login.data

import android.os.Handler
import android.os.Looper
import com.rogger.myapplication.database.DatabaseFake

class FakeDataSource : LoginDataSource {
    override fun login(email: String, password: String, callback: LoginCallback) {

        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth =   DatabaseFake.usersAuth.firstOrNull{ email == it.email }

            when {
                userAuth == null -> {
                    callback.onFailure("Usuario não encontrado")
                }
                userAuth.password != password -> {
                    callback.onFailure("Senha esta incorreta")
                }
                else -> {
                    DatabaseFake.sessionAuth = userAuth
                    callback.onSuccess(userAuth)
                }
            }
            callback.onComplete()
        }, 2000)
    }
}