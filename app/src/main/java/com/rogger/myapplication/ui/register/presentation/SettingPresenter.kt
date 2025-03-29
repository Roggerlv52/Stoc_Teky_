package com.rogger.myapplication.ui.register.presentation

import com.rogger.myapplication.ui.register.Settings
import com.rogger.myapplication.ui.register.data.RegisterCallback
import com.rogger.myapplication.ui.register.data.RegisterRepository

class SettingPresenter(
    private var view: Settings.View?,
    private var repository: RegisterRepository
) : Settings.Presenter {

    override fun createData(name: String, pais: String, moeda: String, comercio: String, termos: Boolean) {

        repository.createSetting(name,pais, moeda, comercio, termos, object : RegisterCallback {

            override fun onSuccess() {
               view?.onsSuccess(name)
            }

            override fun onFailure(message: String) {
                view?.onShowError(message)
            }
            override fun onComplete() {

            }

        })
    }

    override fun onDestroy() {
        view = null
    }
}