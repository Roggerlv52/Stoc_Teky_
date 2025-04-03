package com.rogger.myapplication.ui.setting.data


class SettingRepository(private val dataSource: SettingDataSource) {
    fun deleteAccount(callback: SettingCallback) {
        dataSource.deleteAccount(callback)
    }
}