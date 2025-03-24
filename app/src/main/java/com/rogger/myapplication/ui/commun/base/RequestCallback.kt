package com.rogger.myapplication.ui.commun.base

interface RequestCallback<T> {
    fun onSuccess(data: T)
    fun onFailure(message:String)
    fun onComplete()
}