package com.rogger.myapplication.ui.register

import com.rogger.myapplication.ui.commun.base.BasePresenter
import com.rogger.myapplication.ui.commun.base.BaseView
import com.rogger.myapplication.ui.register.RegisterNameAndPassword.Presenter

interface WelcomeHome {
        interface Presenter : BasePresenter {
                fun success()
        }
        //criar caso de usos
        interface View : BaseView<Presenter> {
                fun showProgress(enable: Boolean)
                fun onFailure(message: String)
                fun gotosuccess()
        }

}