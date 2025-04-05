package com.rogger.myapplication.ui.login.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rogger.myapplication.R
import com.rogger.myapplication.databinding.FragmentResetEnterEmailBinding
import com.rogger.myapplication.ui.commun.base.DependencyInjector
import com.rogger.myapplication.ui.commun.util.TxtWatcher
import com.rogger.myapplication.ui.login.Login
import com.rogger.myapplication.ui.login.presentation.LoginPresenter

class ResetEnterEmailFragment : Fragment(R.layout.fragment_reset_enter_email), Login.View {
    private var binding: FragmentResetEnterEmailBinding? = null
    private var fragamentAttachLiestener : FragmentAttch? = null

    override lateinit var presenter: Login.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentResetEnterEmailBinding.bind(view)

        val repository = DependencyInjector.loginRepository()
        presenter = LoginPresenter(this,repository)

        binding?.let {
           with(it) {
               sendEmail.setOnClickListener {
                   presenter.recoverPassword(resetEditEmail.text.toString())
               }
               resetEditEmail.addTextChangedListener(watcher)
               resetEditEmail.addTextChangedListener(TxtWatcher{
                   displayEmailFailure(null)
               })
           }
        }
    }

    private val watcher = TxtWatcher{
        binding?.sendEmail?.isEnabled = binding?.resetEditEmail?.text.toString().isNotEmpty()
                && binding?.resetEditEmail?.text.toString().isNotEmpty()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentAttch){
            fragamentAttachLiestener = context
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        fragamentAttachLiestener = null

    }

    override fun showProgress(enabled: Boolean) {
       binding?.progressSend?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding?.progressSend?.visibility = View.INVISIBLE
        binding?.resetEmailInput?.error = emailError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {

    }

    override fun onUserAuthenticated() {
        fragamentAttachLiestener?.sussce()
    }

    override fun onUserUnauthorized(message: String) {
        binding?.progressSend?.visibility = View.GONE

    }
}