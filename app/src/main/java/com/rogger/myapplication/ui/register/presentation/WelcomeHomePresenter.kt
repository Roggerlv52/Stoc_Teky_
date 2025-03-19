package com.rogger.myapplication.ui.register.presentation

import com.rogger.myapplication.ui.register.WelcomeHome
import com.rogger.myapplication.ui.register.data.RegisterRepository

class WelcomeHomePresenter(
    private var view: WelcomeHome.View,
    private val repository: RegisterRepository
) : WelcomeHome.Presenter {

    override fun success() {
        view.gotosuccess()
    }

    override fun onDestroy() {

    }
}