package com.rogger.myapplication.ui.splashScreen.view

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.rogger.myapplication.MainActivity
import com.rogger.myapplication.R
import com.rogger.myapplication.databinding.ActivitySplashBinding
import com.rogger.myapplication.ui.commun.base.DependencyInjector
import com.rogger.myapplication.ui.commun.extension.animationEnd
import com.rogger.myapplication.ui.login.view.LoginActivity
import com.rogger.myapplication.ui.splashScreen.Splash
import com.rogger.myapplication.ui.splashScreen.presentation.SplashPresenter

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), Splash.View {
    private lateinit var  binding : ActivitySplashBinding
    override  lateinit var presenter: Splash.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // Define a Activity como full screen, removendo a status bar

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        val repository = DependencyInjector.splashRepository(this)
        presenter = SplashPresenter(this,repository)

        binding.splashImg.animate().apply {
            setListener(animationEnd {
                presenter.authenticated()
            })
            duration = 1000
            alpha(1.0f)
            start()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun goToMainScreen() {
        binding.splashImg.animate().apply {
            setListener(animationEnd {
                val intent = Intent(baseContext, MainActivity::class.java)
                //para tirar ativvidade da frente
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            })
            duration = 2000
            startDelay = 500
            alpha(0.0f)
            start()
        }
    }

    override fun goToLoginScreen() {
        binding.splashImg.animate().apply {
            setListener(animationEnd {
                val intent = Intent(baseContext, LoginActivity::class.java)
                //para tirar ativvidade da frente
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                val options = ActivityOptions.makeCustomAnimation(baseContext,
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                startActivity(intent, options.toBundle())
            })
            duration = 2000
            startDelay = 500
            alpha(0.0f)
            start()
        }
    }


}