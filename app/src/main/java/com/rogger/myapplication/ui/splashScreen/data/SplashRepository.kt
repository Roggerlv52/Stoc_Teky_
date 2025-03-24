package com.rogger.myapplication.ui.splashScreen.data

class SplashRepository(private val dataSource: SplashDataSource) {
    fun checkSession(callback:SplashCallback){
        dataSource.checkSession(callback)
    }
}