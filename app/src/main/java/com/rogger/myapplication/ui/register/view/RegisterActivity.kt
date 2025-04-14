package com.rogger.myapplication.ui.register.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rogger.myapplication.MainActivity
import com.rogger.myapplication.R
import com.rogger.myapplication.databinding.ActivityRegisterBinding
import com.rogger.myapplication.ui.commun.extension.hidekyboard

class RegisterActivity : AppCompatActivity(),
    FragmentAttachLiestener {
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

       val name = intent.getStringExtra("EXTRA_NAME_LOGIN")
       val email = intent.getStringExtra("EXTRA_EMAIL_LOGIN")
        if (name != null && email != null ) {
            goToLSettingScreen(name, email)
        }else{
            if (savedInstanceState == null) {
                replaceFragment(RegisterEmailFragment())
            }
        }

    }

    override fun goToNameAndPasswordScreen(email: String) {
        val fragment = RegisterNamePasswordFragment().apply {
            arguments = Bundle().apply {
                putString(RegisterNamePasswordFragment.KEY_EMAIL, email)
            }
        }
        replaceFragment(fragment)
    }

    override fun goToLSettingScreen(name: String, email: String) {
        val fragment = SettingFragment().apply {
            arguments = Bundle().apply {
                putString(SettingFragment.KEY_NAMESETTING, name)
                putString(SettingFragment.KEY_EMAIL, email)
            }
        }
        replaceFragment(fragment)
    }

    override fun goToWelcomeScreen(name: String) {
        val fragment = RegisterWelcomeFragment().apply {
            arguments = Bundle().apply {
                putString(RegisterWelcomeFragment.KEY_NAME, name)
            }
        }
        replaceFragment(fragment)
    }

    override fun goToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    //troca de fragment
    private fun replaceFragment(fragment: Fragment) {
        if (supportFragmentManager.findFragmentById(R.id.register_fragment) == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.register_fragment, fragment)
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.register_fragment, fragment)
                addToBackStack(null)
                commit()
            }
        }
        hidekyboard()
    }
}
