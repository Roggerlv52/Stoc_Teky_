package com.rogger.myapplication.ui.gestao.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rogger.myapplication.R
import com.rogger.myapplication.databinding.FragmentGestaoBinding
import com.rogger.myapplication.ui.commun.base.DependencyInjector
import com.rogger.myapplication.ui.gestao.Gestao
import com.rogger.myapplication.ui.gestao.presentation.GestaoPresenter
import com.rogger.myapplication.ui.login.view.LoginActivity


class FragmentGestao : Fragment(R.layout.fragment_gestao), Gestao.View {
    private var binding: FragmentGestaoBinding? = null
    override lateinit var presenter: Gestao.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGestaoBinding.bind(view)

        var repository = DependencyInjector.gestaoRepository()
        presenter = GestaoPresenter(this, repository)
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

        override fun onDestroyView() {
            presenter.onDestroy()
            binding = null
            super.onDestroyView()
        }

    }