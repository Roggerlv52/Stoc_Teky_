package com.rogger.myapplication.ui.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.rogger.myapplication.R
import com.rogger.myapplication.databinding.FragmentRegisterEmailBinding
import com.rogger.myapplication.ui.commun.base.DependencyInjector
import com.rogger.myapplication.ui.commun.util.TxtWatcher
import com.rogger.myapplication.ui.register.RegisterEmail
import com.rogger.myapplication.ui.register.presentation.RegisterEmailPresenter

class RegisterEmailFragment : Fragment(R.layout.fragment_register_email),
    RegisterEmail.View {
    private var binding: FragmentRegisterEmailBinding? = null
    private var fragamentAttachLiestener : FragamentAttachLiestener? = null

    override lateinit var presenter: RegisterEmail.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterEmailBinding.bind(view)

        val repositer = DependencyInjector.registerEmailRepositoy()
        presenter = RegisterEmailPresenter(this,repositer)

        //buscar as referencias em baixo
        binding?.let {

            with(it){
                registerTxtLogin.setOnClickListener {
                    activity?.finish()
                }
                registerBtnNext.setOnClickListener {
                    presenter.create(
                        registerEditEmail.text.toString()
                    )
                }
                registerEditEmail.addTextChangedListener(watcher)
                registerEditEmail.addTextChangedListener(TxtWatcher{
                    displayEmailFailure(null)
                })
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragamentAttachLiestener){
            fragamentAttachLiestener = context
        }
    }
    override fun onDestroy() {
        binding = null
        fragamentAttachLiestener = null
        presenter.onDestroy()
        super.onDestroy()
    }
    private val watcher = TxtWatcher{
        binding?.registerBtnNext?.isEnabled = binding?.registerEditEmail?.text.toString().isNotEmpty()
                && binding?.registerEditEmail?.text.toString().isNotEmpty()
    }

    override fun showProgress(enabled: Boolean) {
        if (enabled) {
            binding?.progressbarEmail?.visibility = View.VISIBLE
        } else {
            binding?.progressbarEmail?.visibility = View.GONE
        } //
    }
    override fun displayEmailFailure(emailError: Int?) {
        binding?.progressbarEmail?.visibility = View.GONE
        binding?.registerEditEmailInput?.error = emailError?.let { getString(it) }
    }
    override fun onEmailFalure(message: String) {
        binding?.progressbarEmail?.visibility = View.GONE
        binding?.registerEditEmailInput?.error = message
    }
    override fun goToNameAndPasswordScreen(email: String) {
        //ir para proxima tela
        binding?.progressbarEmail?.visibility = View.GONE
        fragamentAttachLiestener?.goToNameAndpasswordScreen(email)
    }
}