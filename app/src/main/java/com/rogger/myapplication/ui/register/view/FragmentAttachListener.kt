package com.rogger.myapplication.ui.register.view

import android.net.Uri

interface FragmentAttachLiestener {
        fun goToNameAndPasswordScreen(email : String)
        fun goToLSettingScreen(name:String)
        fun goToWelcomeScreen(name:String)
        fun goToMainScreen()
    }