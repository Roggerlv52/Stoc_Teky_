package com.rogger.myapplication.ui.register.data

class RegisterRepository(private val dataSource : RegisterDataSource) {
    fun create(email : String,callback : RegisterCallback){
        dataSource.create(email,callback)
    }
    fun create(email : String, name : String, password : String, callback : RegisterCallback) {
        dataSource.create(email, name, password, callback)
    }

    fun createSetting( name:String ,pais:String,moeda:String,comercio:String,termos:Boolean,callback: RegisterCallback){
        dataSource.createSetting(name,pais,moeda,comercio,termos,callback)
    }
}