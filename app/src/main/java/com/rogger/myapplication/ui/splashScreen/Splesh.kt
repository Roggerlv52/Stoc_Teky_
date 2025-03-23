package com.rogger.myapplication.ui.splashScreen

import com.rogger.myapplication.ui.commun.base.BasePresenter
import com.rogger.myapplication.ui.commun.base.BaseView

interface Splash {
    interface Presenter : BasePresenter {
        fun authenticated()
    }
    interface View : BaseView<Presenter> {
        fun goToMainScreen()
        fun goToLoginScreen()
    }
}