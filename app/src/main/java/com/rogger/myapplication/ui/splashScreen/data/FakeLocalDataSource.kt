package com.rogger.myapplication.ui.splashScreen.data

import androidx.room.Database
import com.rogger.myapplication.database.DatabaseFake

class FakeLocalDataSource : SplashDataSource{
    override fun sesscion(callback: SplashCallback) {
        if (DatabaseFake.sessionAuth != null){
            callback.onSuccess()
        }else{
            callback.onFalure()
        }
    }
}