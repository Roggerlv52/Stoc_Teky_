package com.rogger.myapplication.ui.register.data

interface RegisterCallback {
    fun onSuccess() // caso de sucesso
    fun onFailure(message : String) // para messagem de erro do cervidor
    fun onComplete()
}