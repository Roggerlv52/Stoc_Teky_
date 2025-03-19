package com.rogger.myapplication.ui.login.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.rogger.myapplication.MainActivity
import com.rogger.myapplication.R
import com.rogger.myapplication.databinding.ActivityLoginBinding
import com.rogger.myapplication.ui.commun.BaseActivit
import com.rogger.myapplication.ui.commun.base.DependencyInjector
import com.rogger.myapplication.ui.commun.util.TxtWatcher
import com.rogger.myapplication.ui.login.Login
import com.rogger.myapplication.ui.login.presentation.LoginPresenter
import com.rogger.myapplication.ui.register.view.RegisterActivity

class LoginActivity : BaseActivit(), Login.View {
    private var binding: ActivityLoginBinding? = null
    override lateinit var presenter: Login.Presenter

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.loginBtnEnter?.isEnabled = false
        presenter = LoginPresenter(this, DependencyInjector.loginRepository())

        binding?.let {

            with(it) {

                loginEditEmail.addTextChangedListener(watcher)
                loginEditEmail.addTextChangedListener(TxtWatcher {
                    displayEmailFailure(null)

                })
                loginEditPassword.addTextChangedListener(watcher)
                loginEditPassword.addTextChangedListener(TxtWatcher {
                    displayPasswordFailure(null)

                })
                loginBtnEnter.setOnClickListener {
                    // Chamar o PRESENTER
                    presenter.login(
                        loginEditEmail.text.toString(),
                        loginEditPassword.text.toString()
                    )

                }
                loginTxtRegister.setOnClickListener {
                    goToRegisterScreen()
                }
                // Alternar visibilidade da senha e trocar ícones
                loginEditPasswordInput.setEndIconOnClickListener {
                    val isPasswordVisible = loginEditPassword.inputType == android.text.InputType.TYPE_CLASS_TEXT
                    if (isPasswordVisible) {
                        loginEditPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                        loginEditPasswordInput.endIconDrawable = ContextCompat.getDrawable(this@LoginActivity, R.drawable.ic_visibility_off_24)
                    } else {
                        loginEditPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT
                        loginEditPasswordInput.endIconDrawable = ContextCompat.getDrawable(this@LoginActivity, R.drawable.ic_visibility_24)
                    }
                    loginEditPassword.setSelection(loginEditPassword.text?.length ?: 0)
                }
            }
        }
    }


    private val watcher = TxtWatcher {
        val isEmailFilled = binding?.loginEditEmail?.text.toString().isNotEmpty()
        val isPasswordFilled = binding?.loginEditPassword?.text.toString().isNotEmpty()
        binding?.loginBtnEnter?.isEnabled = isEmailFilled && isPasswordFilled

    }

    private fun goToRegisterScreen() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun showProgress(enabled: Boolean) {
        if (enabled) {
            binding?.progressbarLogin?.visibility = View.VISIBLE
        } else {
            binding?.progressbarLogin?.visibility = View.GONE
        }
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding?.loginEditEmailInput?.error = emailError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding?.loginEditPasswordInput?.error = passwordError?.let { getString(it) }
    }

    override fun onUserAuthenticated() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onUserUnauthoried(message: String) {
        binding?.progressbarLogin?.visibility = View.GONE
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}