package com.rogger.myapplication.ui.register.view

interface FragmentAttachLiestener {
        fun goToNameAndPasswordScreen(email : String)
        fun goToLSettingScreen(name:String, email: String)
        fun goToWelcomeScreen(name:String)
        fun goToMainScreen()
    }