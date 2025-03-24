package com.rogger.myapplication.ui.splashScreen.presentation

import com.rogger.myapplication.ui.splashScreen.Splash
import com.rogger.myapplication.ui.splashScreen.data.SplashCallback
import com.rogger.myapplication.ui.splashScreen.data.SplashRepository

class SplashPresenter(
    private var view: Splash.View?,
    private val reository : SplashRepository
):Splash.Presenter {
    override fun authenticated() {
        reository.checkSession(object : SplashCallback {
            override fun onSuccess() {
                view?.goToMainScreen()
            }

            override fun onFailure() {
               view?.goToLoginScreen()
            }

        })
    }
    override fun onDestroy() {
        view = null
    }

}