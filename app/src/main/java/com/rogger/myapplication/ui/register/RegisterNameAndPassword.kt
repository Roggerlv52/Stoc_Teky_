package com.rogger.myapplication.ui.register

import android.support.annotation.StringRes
import com.rogger.myapplication.ui.commun.base.BasePresenter
import com.rogger.myapplication.ui.commun.base.BaseView

interface RegisterNameAndPassword {
    interface Presenter : BasePresenter {
        fun create( email: String,name: String, password: String, confirme : String)
    }
    interface View : BaseView<Presenter> {

        //criar caso de usos
        fun showProgress(enable : Boolean)
        fun displayNamelFailure(@StringRes nameError : Int?)
        fun displayPasswordFailure(@StringRes passError : Int?)
        fun onCreateFailure(message : String)
        fun onCreateSuccess(name: String)
    }
}