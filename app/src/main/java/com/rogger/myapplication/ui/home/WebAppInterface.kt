package com.rogger.myapplication.ui.home

import android.webkit.JavascriptInterface
import androidx.navigation.fragment.findNavController
import com.rogger.myapplication.R

class WebAppInterface(private val fragment: HomeFragment) {
    @JavascriptInterface
    fun openScreen(screenName: String) {

        // Como isso pode ser chamado fora do UI Thread, certifique-se de rodar no Main
        fragment.requireActivity().runOnUiThread  {
            val navController = fragment.findNavController()
            val destination = when (screenName) {
                "estoque" -> R.id.nav_estoque
                "fornecedores" -> R.id.nav_fornecedores
                "fiscais" -> R.id.nav_notas
                "relatórios" -> R.id.nav_relatorios
                "financeiro" -> R.id.nav_financeiro
                "locação" -> R.id.nav_locacao
                "promoções" -> R.id.nav_promocoes
                "automação" -> R.id.nav_outomacao
                else -> null
            }
            destination?.let {
                navController.navigate(it)
            }
        }
    }
}