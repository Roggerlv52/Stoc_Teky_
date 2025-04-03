package com.rogger.myapplication.ui.setting.data

interface SettingCallback {
    fun onSuccess() // caso de sucesso
    fun onFailure(message : String) // para messagem de erro do cervidor
    fun onComplete()
}