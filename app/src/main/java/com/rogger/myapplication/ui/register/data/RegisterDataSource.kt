package com.rogger.myapplication.ui.register.data

interface RegisterDataSource {
    fun create(email : String, callback: RegisterCallback)
    fun create( email:  String,name : String, password : String, callback: RegisterCallback)
    fun createSetting(name:String,pais:String,moeda:String,comercio:String,termos:Boolean,callback: RegisterCallback)
}