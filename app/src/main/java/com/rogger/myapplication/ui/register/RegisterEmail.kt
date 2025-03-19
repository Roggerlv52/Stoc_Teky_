package com.rogger.myapplication.ui.register

import android.support.annotation.StringRes
import com.rogger.myapplication.ui.commun.base.BasePresenter
import com.rogger.myapplication.ui.commun.base.BaseView

interface RegisterEmail {

    interface Presenter : BasePresenter {
        fun create(email: String)
    }

    interface View : BaseView<Presenter> {//criar caso de usos
    fun showProgress(enable : Boolean)

        fun displayEmailFailure(@StringRes emailError : Int?)

        fun onEmailFalure(message : String)

        fun goToNameAndPasswordScreen(email: String)

    }
}