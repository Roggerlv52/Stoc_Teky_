package com.rogger.myapplication.ui.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rogger.myapplication.R
import com.rogger.myapplication.databinding.FragmentRegisterWelcomeBinding
import com.rogger.myapplication.ui.register.WelcomeHome

class RegisterWelcomeFragment : Fragment(R.layout.fragment_register_welcome),
    WelcomeHome.View {

    companion object {
        const val KEY_NAME = "key_name"
    }

    private var fragamentAttachListener: FragmentAttachLiestener? = null
    private var binding: FragmentRegisterWelcomeBinding? = null

    override lateinit var presenter: WelcomeHome.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterWelcomeBinding.bind(view)

        val name = arguments?.getString(KEY_NAME) ?: throw IllegalArgumentException("name not found")

        binding?.let {
            with(it) {
                registerTxtWelcome.text = getString(R.string.welcome_to_my_app, name)
                registerBtnNextWlcom.isEnabled = true
                registerBtnNextWlcom.setOnClickListener {
                    fragamentAttachListener?.goToMainScreen()
                }
                registerTxtWelcome.setOnClickListener {
                    activity?.finish()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachLiestener) {
            fragamentAttachListener = context
        }
    }

    override fun showProgress(enable: Boolean) {
        binding?.progressbarWelcome?.visibility = if (enable) View.VISIBLE else View.GONE
    }

    override fun onFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun gotosuccess() {
        fragamentAttachListener?.goToMainScreen()
    }
}
