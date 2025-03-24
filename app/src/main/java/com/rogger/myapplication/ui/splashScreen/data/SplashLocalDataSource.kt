package com.rogger.myapplication.ui.splashScreen.data

import android.content.Context
import android.content.SharedPreferences

class SplashLocalDataSource(private val context: Context) : SplashDataSource{

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("app_shared_prefs", Context.MODE_PRIVATE)
    }
    override fun checkSession(callback: SplashCallback) {
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false) // Substitua "is_logged_in" pela sua chave
        if (isLoggedIn) {
            callback.onSuccess()
        } else {
            callback.onFailure()
        }
    }
    // Método para definir o estado de login (use no seu LoginRepository após o login bem-sucedido)
    fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("is_logged_in", isLoggedIn).apply()
    }
    // Método para limpar o estado de login (use no logout, se necessário)
    fun clearSession() {
        sharedPreferences.edit().putBoolean("is_logged_in", false).apply()
    }
}