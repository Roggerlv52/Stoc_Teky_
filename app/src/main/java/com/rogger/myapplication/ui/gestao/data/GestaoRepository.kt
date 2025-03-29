package com.rogger.myapplication.ui.gestao.data


class GestaoRepository(private val dataSource: GestaoDataSource) {
    fun deleteAccount(callback: GestaoCallback) {
        dataSource.deleteAccount(callback)
    }
}