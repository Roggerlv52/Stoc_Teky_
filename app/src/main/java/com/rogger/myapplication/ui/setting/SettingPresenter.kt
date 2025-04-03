package com.rogger.myapplication.ui.setting

import com.rogger.myapplication.ui.setting.data.SettingCallback
import com.rogger.myapplication.ui.setting.data.SettingRepository

class SettingPresenter(private var view: Setting.View?,
                       private val repository: SettingRepository): Setting.Presenter {
    override fun deleteAccount() {
       repository.deleteAccount(object : SettingCallback {
           override fun onSuccess() {
               view?.showDeletionSuccess()
           }

           override fun onFailure(message: String) {
               view?.showDeletionFailure(message)
           }

           override fun onComplete() {
               view?.showProgress(false)
           }

       })
    }

    override fun onDestroy() {
        view = null
    }

}