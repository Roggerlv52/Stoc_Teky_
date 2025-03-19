package com.rogger.myapplication.ui.login.data

interface LoginDataSource {
    fun login(email : String, password : String, callback: LoginCallback)
}