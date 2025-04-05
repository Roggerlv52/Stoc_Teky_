package com.rogger.myapplication.ui.setting.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.rogger.myapplication.R
import com.rogger.myapplication.databinding.FragmentSettingBinding
import com.rogger.myapplication.ui.commun.base.DependencyInjector
import com.rogger.myapplication.ui.login.view.LoginActivity
import com.rogger.myapplication.ui.register.presentation.SettingRegisterPresenter
import com.rogger.myapplication.ui.setting.Setting
import com.rogger.myapplication.ui.setting.SettingPresenter

class SettingFragment : Fragment(R.layout.fragment_setting), Setting.View {
    private var binding: FragmentSettingBinding? = null
    override lateinit var presenter: Setting.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingBinding.bind(view)

        val repository = DependencyInjector.settingRepository()
        presenter = SettingPresenter(this, repository)

        binding?.txt8?.setOnClickListener {
            showConfirmationDialog()
        }
        binding?.switchThemeDark?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Ativa o modo escuro
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Ativa o modo claro
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
    private fun showConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Excluir Conta")
            .setMessage("Tem certeza de que deseja excluir sua conta? Essa ação não poderá ser desfeita.")
            .setPositiveButton("Sim") { _, _ ->
                presenter.deleteAccount()
            }
            .setNegativeButton("Não", null)
            .show()
    }

    override fun showProgress(enabled: Boolean) {

    }

    override fun showDeletionSuccess() {
        AlertDialog.Builder(requireContext())
            .setTitle("Conta Excluída")
            .setMessage("Sua conta foi deletada com sucesso.")
            .setPositiveButton("Voltar") { _, _ ->
                // Navega para a tela de login; por exemplo, iniciando LoginActivity
                val intent = Intent(requireContext(), LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
            }
            .setCancelable(false)
            .show()
    }

    override fun showDeletionFailure(message: String) {
        Toast.makeText(requireContext(), "Erro: $message", Toast.LENGTH_SHORT).show()
    }


}