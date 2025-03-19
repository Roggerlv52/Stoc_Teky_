package com.rogger.myapplication.ui.login.data

import com.rogger.myapplication.molds.UserAuth

interface LoginCallback {
    fun onSuccess(userAuth: UserAuth) // caso de sucesso
    fun onFailure(message : String) // para messagem de erro do cervidor
    fun onComplete()
}