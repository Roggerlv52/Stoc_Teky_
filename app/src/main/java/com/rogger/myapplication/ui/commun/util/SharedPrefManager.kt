package com.rogger.myapplication.ui.commun.util

import android.content.Context
import android.content.SharedPreferences

object SharedPrefManager {
    const val PREFS_NAME = "my_prefs"
    private lateinit var sharedPreferences: SharedPreferences

    // Inicializa o SharedPreferences; ideal chamar no onCreate da Application ou Activity principal
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Método para salvar uma string
    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()

    }

    // Método para recuperar uma string
    fun getString(key: String, default: String? = null): String? {
        return sharedPreferences.getString(key, default)
    }
    // Método para limpar todas as preferências
    fun clearPreferences() {
        sharedPreferences.edit().clear().apply()
    }

}