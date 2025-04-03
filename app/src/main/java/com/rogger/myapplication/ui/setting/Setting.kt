package com.rogger.myapplication.ui.setting

import com.rogger.myapplication.ui.commun.base.BasePresenter
import com.rogger.myapplication.ui.commun.base.BaseView

interface Setting {
    interface Presenter : BasePresenter {
        fun deleteAccount()
    }
    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun showDeletionSuccess()
        fun showDeletionFailure(message: String)
    }
}