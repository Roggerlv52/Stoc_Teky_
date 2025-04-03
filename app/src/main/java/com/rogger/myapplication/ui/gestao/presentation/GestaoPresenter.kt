package com.rogger.myapplication.ui.gestao.presentation

import com.rogger.myapplication.ui.gestao.Gestao
import com.rogger.myapplication.ui.gestao.data.GestaoRepository
import com.rogger.myapplication.ui.gestao.data.GestaoCallback


class GestaoPresenter(
    private var view: Gestao.View?,
    private val repository: GestaoRepository
) : Gestao.Presenter {
    override fun deleteAccount() {
        view?.showProgress(true)
        repository.deleteAccount(object : GestaoCallback{
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