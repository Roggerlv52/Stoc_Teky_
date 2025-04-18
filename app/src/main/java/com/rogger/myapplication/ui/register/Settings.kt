package com.rogger.myapplication.ui.register

import com.rogger.myapplication.ui.commun.base.BasePresenter
import com.rogger.myapplication.ui.commun.base.BaseView

interface Settings {
    interface Presenter : BasePresenter {
        fun create(name: String, pais: String, moeda: String, comercio: String, termos: Boolean)
    }

    //criar caso de usos
    interface View : BaseView<Presenter> {
        fun onsSuccess(name: String)
    }

}