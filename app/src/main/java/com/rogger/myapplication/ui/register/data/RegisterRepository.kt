package com.rogger.myapplication.ui.register.data

class RegisterRepository(private val dataSource : RegisterDataSource) {
    fun createEmail(email : String, callback : RegisterCallback){
        dataSource.createEmail(email, callback)
    }
    fun createNameAndPassword(email : String, name : String, password : String, callback : RegisterCallback) {
        dataSource.createNameAndPassword(email, name, password, callback)
    }

    fun createSetting( name: String,email: String?, pais: String, moeda: String, comercio: String, termos: Boolean, callback: RegisterCallback) {
        dataSource.createSetting( name,email, pais, moeda, comercio, termos, callback)
    }
    fun deleteAccount(callback: RegisterCallback) {
        dataSource.deleteAccount(callback)
    }

}
