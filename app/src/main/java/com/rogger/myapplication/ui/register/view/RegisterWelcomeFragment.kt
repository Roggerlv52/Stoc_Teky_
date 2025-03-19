package com.rogger.myapplication.ui.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rogger.myapplication.R
import com.rogger.myapplication.databinding.FragmentRegisterWelcomeBinding
import com.rogger.myapplication.ui.register.RegisterNameAndPassword
import com.rogger.myapplication.ui.register.WelcomeHome

class RegisterWelcomeFragment : Fragment(R.layout.fragment_register_welcome),
    WelcomeHome.View {
    companion object {
        const val KEY_NAME = "key_name"
    }

    private var fragamentAttachLiestener: FragamentAttachLiestener? = null
    private var binding: FragmentRegisterWelcomeBinding? = null
    override lateinit var presenter: WelcomeHome.Presenter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterWelcomeBinding.bind(view)

        val name =
            arguments?.getString(KEY_NAME) ?: throw IllegalArgumentException("name not found")

        binding?.let {
            with(it) {
                registerBtnNextWlcom.isEnabled = true
                registerTxtWelcome.text = getString(R.string.welcome_to_my_app, name)
                registerBtnNextWlcom.setOnClickListener {
                    fragamentAttachLiestener?.goToMainScreen()
                }
                registerTxtWelcome.setOnClickListener {
                    activity?.finish()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragamentAttachLiestener) {
            fragamentAttachLiestener = context
        }
        if (context is FragamentAttachLiestener) {
            fragamentAttachLiestener = context
        }
    }

    override fun showProgress(enable: Boolean) {
        if (enable) {
            binding?.progressbarWelcome?.visibility = View.VISIBLE
        } else {
            binding?.progressbarWelcome?.visibility = View.GONE
        } //
    }

    override fun onFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun gotosuccess() {
        fragamentAttachLiestener?.goToMainScreen()
    }

}