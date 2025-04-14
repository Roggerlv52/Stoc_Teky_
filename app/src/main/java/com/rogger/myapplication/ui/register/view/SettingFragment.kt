package com.rogger.myapplication.ui.register.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rogger.myapplication.R
import com.rogger.myapplication.databinding.LayoutSettingBinding
import com.rogger.myapplication.ui.commun.base.DependencyInjector
import com.rogger.myapplication.ui.register.Settings
import com.rogger.myapplication.ui.register.presentation.SettingRegisterPresenter
import com.rogger.myapplication.ui.terms.TermsActivity

class SettingFragment : Fragment(R.layout.layout_setting), Settings.View {
    companion object {
        const val KEY_NAMESETTING = "key_name_setting"
        const val KEY_EMAIL = "key_email"
    }
    private var name: String? = null
    private var email: String? = null
    private var binding: LayoutSettingBinding? = null
    private var fragmentAttachListener: FragmentAttachLiestener? = null

    override lateinit var presenter: Settings.Presenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachLiestener) {
            fragmentAttachListener = context
        } else {
            throw RuntimeException("$context deve implementar FragamentAttachLiestener")
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = LayoutSettingBinding.bind(view)

        name = arguments?.getString(KEY_NAMESETTING)
            ?: throw IllegalArgumentException("nome not found")
        email = arguments?.getString(KEY_EMAIL)
        if (name == null) {
            name = arguments?.getString("EXTRA_NAME_LOGIN")
            email = arguments?.getString("EXTRA_EMAIL_LOGIN")
        }
        val repositer = DependencyInjector.registerEmailRepository()
        presenter = SettingRegisterPresenter(this, repositer)

        binding = LayoutSettingBinding.bind(view)

        binding?.let {
            with(it) {

                webView.settings.javaScriptEnabled = true
                webView.settings.domStorageEnabled = true // Adicione esta linha

                webView.webViewClient = object : WebViewClient() {
                    // Impede que o WebView carregue URLs arbitrárias
                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        return false // Deixa o WebView lidar com a URL
                    }

                    // Lida com erros
                    override fun onReceivedError(
                        view: WebView?,
                        errorCode: Int,
                        description: String?,
                        failingUrl: String?
                    ) {
                        Toast.makeText(
                            context,
                            "Erro ao carregar URL: $description",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } // Importante: Defina um WebViewClient
                webView.addJavascriptInterface(
                    WebAppInterface(fragmentAttachListener!!, webView),
                    "Android"
                ) // Adicione a interface JavaScript
                webView.loadUrl("file:///android_asset/index.html")
            }
        }
    }

    override fun onsSuccess(name: String) {
       // fragmentAttachLiestener?.goToLSettingScreen(name)
    }

    override fun onShowError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.d("SettingFragment", "onShowError: $message")

    }
    // JavaScript Interface
    inner class WebAppInterface(
        private val fragmentAttachListener: FragmentAttachLiestener,
        private val webView: WebView
    ) {

        @JavascriptInterface
        fun print() {
            // Run on the UI thread because we're interacting with UI elements
            webView.post {
                createWebPrintJob(webView)
            }
        }

        @SuppressLint("SuspiciousIndentation")
        @JavascriptInterface
        fun termsScreen() {
            val intent = Intent(requireContext(), TermsActivity::class.java)
            context?.startActivity(intent)
        }

        @JavascriptInterface
        fun gotoWelcoScreen(pais: String, moeda: String, segmento: String, termos: Boolean) {
            activity?.runOnUiThread {
                presenter.createData(name!!,email, pais, moeda, segmento, termos)
                fragmentAttachListener.goToWelcomeScreen(name!!)

            }
        }

        private fun createWebPrintJob(webView: WebView) {
            val printManager =
                requireContext().getSystemService(Context.PRINT_SERVICE) as PrintManager
            val jobName = "${requireContext().getString(R.string.app_name)} Document"
            val printAdapter = webView.createPrintDocumentAdapter(jobName)
            val printAttributes =
                PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                    .setResolution(PrintAttributes.Resolution("pdf", "pdf", 300, 300))
                    .setMinMargins(PrintAttributes.Margins.NO_MARGINS).build()
            printManager.print(jobName, printAdapter, printAttributes)
        }
    }
}