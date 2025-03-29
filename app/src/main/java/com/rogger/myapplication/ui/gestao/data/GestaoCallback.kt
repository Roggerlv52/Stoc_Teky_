package com.rogger.myapplication.ui.gestao.data

interface GestaoCallback {
    fun onSuccess() // caso de sucesso
    fun onFailure(message : String) // para messagem de erro do cervidor
    fun onComplete()
}