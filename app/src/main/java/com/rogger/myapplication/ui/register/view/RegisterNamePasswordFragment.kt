package com.rogger.myapplication.ui.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rogger.myapplication.R
import com.rogger.myapplication.databinding.FragmentRegisterNamePasswordBinding
import com.rogger.myapplication.ui.commun.base.DependencyInjector
import com.rogger.myapplication.ui.commun.util.TxtWatcher
import com.rogger.myapplication.ui.register.RegisterNameAndPassword
import com.rogger.myapplication.ui.register.presentation.RegisterNameAndPasswordPresenter

class RegisterNamePasswordFragment : Fragment(R.layout.fragment_register_name_password),
    RegisterNameAndPassword.View {

    companion object {
        const val KEY_EMAIL = "key_email"
    }

    private var fragamentAttachLiestener: FragamentAttachLiestener? = null
    private var _binding: FragmentRegisterNamePasswordBinding? = null

    override lateinit var presenter: RegisterNameAndPassword.Presenter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRegisterNamePasswordBinding.bind(view)

        val repository = DependencyInjector.registerEmailRepositoy()
        presenter = RegisterNameAndPasswordPresenter(this, repository)

        val email =
            arguments?.getString(KEY_EMAIL) ?: throw IllegalArgumentException("email not found")
        _binding?.let {
            with(it) {
                registerTxtLogin.setOnClickListener {
                    activity?.finish()
                }
                registerBtnPassword.setOnClickListener {

                    presenter.createNameAndPassword(email,registerEditName.text.toString(),
                        registerEditPassword.text.toString(), registerEditComfirme.text.toString())

                }
                registerEditName.addTextChangedListener(watcher)
                registerEditPassword.addTextChangedListener(watcher)
                registerEditComfirme.addTextChangedListener(watcher)

                registerEditName.addTextChangedListener(TxtWatcher {
                    displayNameFailure(null)
                })
                registerEditPassword.addTextChangedListener(TxtWatcher {
                    displayPasswordFailure(null)
                })
                registerEditComfirme.addTextChangedListener(TxtWatcher {
                    displayPasswordFailure(null)
                })
                // Alternar visibilidade da senha e trocar ícones
                registerEditPasswordInput.setEndIconOnClickListener {
                    val isPasswordVisible =
                        registerEditPassword.inputType == android.text.InputType.TYPE_CLASS_TEXT
                    if (isPasswordVisible) {
                        registerEditPassword.inputType =
                            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                        registerEditPasswordInput.endIconDrawable = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_visibility_off_24
                        )
                    } else {
                        registerEditPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT
                        registerEditPasswordInput.endIconDrawable =
                            ContextCompat.getDrawable(requireContext(), R.drawable.ic_visibility_24)
                    }
                    registerEditPassword.setSelection(registerEditPassword.text?.length ?: 0)
                }
                // Alternar visibilidade da senha e trocar ícones de confirmação
                registerEditConfirmeInput.setEndIconOnClickListener {
                    val isPasswordVisible =
                        registerEditComfirme.inputType == android.text.InputType.TYPE_CLASS_TEXT
                    if (isPasswordVisible) {
                        registerEditComfirme.inputType =
                            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                        registerEditConfirmeInput.endIconDrawable = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_visibility_off_24
                        )
                    } else {
                        registerEditComfirme.inputType = android.text.InputType.TYPE_CLASS_TEXT
                        registerEditConfirmeInput.endIconDrawable =
                            ContextCompat.getDrawable(requireContext(), R.drawable.ic_visibility_24)
                    }
                    registerEditComfirme.setSelection(registerEditComfirme.text?.length ?: 0)
                }


            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragamentAttachLiestener) {
            fragamentAttachLiestener = context
        }
    }

    override fun showProgress(enable: Boolean) {
            _binding?.progressbarEmailAndPassword?.visibility = if (enable) View.VISIBLE else View.GONE
    }

    override fun displayNameFailure(nameError: Int?) {
        _binding?.progressbarEmailAndPassword?.visibility = View.GONE
        _binding?.registerEditNameInput?.error = nameError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passError: Int?) {
        _binding?.progressbarEmailAndPassword?.visibility = View.GONE
        _binding?.registerEditPasswordInput?.error = passError?.let { getString(it) }
    }

    override fun onCreateFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateSuccess(name: String) {
        _binding?.progressbarEmailAndPassword?.visibility = View.GONE
        fragamentAttachLiestener?.goToLSettingScreen(name)

    }


    private val watcher = TxtWatcher {
        _binding?.registerBtnPassword?.isEnabled =
            _binding?.registerEditName?.text.toString().isNotEmpty()
                    && _binding?.registerEditPassword?.text.toString().isNotEmpty()
                    && _binding?.registerEditComfirme?.text.toString().isNotEmpty()
    }

}