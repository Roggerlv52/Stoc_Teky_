package com.rogger.myapplication.ui.register.data

import android.os.Handler
import android.os.Looper
import com.rogger.myapplication.database.DatabaseFake
import com.rogger.myapplication.molds.SettingMoldel
import com.rogger.myapplication.molds.UserAuth
import java.util.UUID

class FakeRegisterDataSource : RegisterDataSource {
    override fun create(email: String, callback: RegisterCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = DatabaseFake.usersAuth.firstOrNull { email == it.email }

            if (userAuth == null){
                callback.onSuccess()
            }else{
                callback.onFailure("Usuario já cadastrado")
            }

            callback.onComplete()
        }, 2000)
    }

    override fun create(email: String, name: String, password: String, callback: RegisterCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = DatabaseFake.usersAuth.firstOrNull { email == it.email }

            if (userAuth != null){
                callback.onFailure("Usuario já existe")
            }else{
                val newUser = UserAuth(UUID.randomUUID().toString(),email,name,password)
                val created = DatabaseFake.usersAuth.add(newUser)
                if (created){
                    //Database.sessionAuth = newUser



                    callback.onSuccess()
                }else{
                    callback.onFailure("Erro interno no servidor.")
                }
            }
            callback.onComplete()
        },2000)
    }

    override fun createSetting(
        name: String,
        pais: String,
        moeda: String,
        comercio: String,
        termos: Boolean,
        callback: RegisterCallback
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            //val userAuth = DatabaseFake.usersString.add(SettingMoldel(pais,moeda,comercio,termos))
/*
            if (userAuth == null){
                callback.onSuccess()
            }else{
                callback.onFailure("Usuario já cadastrado")
            }
 */
            callback.onComplete()
        }, 2000)
    }
}