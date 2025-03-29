package com.rogger.myapplication.ui.login

import android.content.Context
import android.support.annotation.StringRes
import com.rogger.myapplication.ui.commun.base.BasePresenter
import com.rogger.myapplication.ui.commun.base.BaseView

interface Login {
    //camada de presenter
    interface Presenter : BasePresenter {
        fun login(email : String, password : String)
    }

    // camada de view
    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayEmailFailure(@StringRes emailError: Int?)
        fun displayPasswordFailure(@StringRes passwordError: Int?)
        fun onUserAuthenticated()
        fun onUserUnauthorized(message : String)

    }
}