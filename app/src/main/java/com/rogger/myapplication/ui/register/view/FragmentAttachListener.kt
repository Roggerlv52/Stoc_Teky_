package com.rogger.myapplication.ui.register.view

interface FragamentAttachLiestener {
    fun goToNameAndpasswordScreen(email : String)
    fun goToLSettingScreen(name:String)
    fun goToWelcomeScreen(name:String)
    fun goToMainScreen()
}