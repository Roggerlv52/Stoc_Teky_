package com.rogger.myapplication.ui.splashScreen.data

class SplashRepository(private val dataSource: SplashDataSource) {
    fun sesssion(callback:SplashCallback){
        dataSource.sesscion(callback)
    }
}