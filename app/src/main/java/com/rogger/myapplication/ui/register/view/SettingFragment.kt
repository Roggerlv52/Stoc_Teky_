package com.rogger.myapplication.ui.register.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
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
import com.rogger.myapplication.ui.register.presentation.SettingPresenter
import com.rogger.myapplication.ui.terms.TermsActivity

class SettingFragment : Fragment(R.layout.layout_setting), Settings.View {
    companion object {
        const val KEY_NAMESETTING = "key_name_setting"
    }


    private var binding: LayoutSettingBinding? = null
    private var fragamentAttachLiestener: FragamentAttachLiestener? = null

    override lateinit var presenter: Settings.Presenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragamentAttachLiestener) {
            fragamentAttachLiestener = context
        } else {
            throw RuntimeException("$context deve implementar FragamentAttachLiestener")
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LayoutSettingBinding.bind(view)
        val name = arguments?.getString(KEY_NAMESETTING)
            ?: throw IllegalArgumentException("nome not found")

        val repositer = DependencyInjector.registerEmailRepositoy()
        presenter = SettingPresenter(this, repositer)

        binding = LayoutSettingBinding.bind(view)

        binding?.let {
            with(it) {

                //  fragamentAttachLiestener?.goToWelcomeScreen(name)
                presenter.createName(name, "Pt", "Euro", "fabrica", true)

                webView.settings.javaScriptEnabled = true
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
                    WebAppInterface(requireContext(), webView, name, fragamentAttachLiestener),
                    "Android"
                ) // Adicione a interface JavaScript
                webView.loadUrl("file:///android_asset/index.html")
            }
        }

    }

    override fun onsSuccess(name: String) {
    }

    override fun onShowError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    }

    // JavaScript Interface
    class WebAppInterface(
        private val context: Context, private val webView: WebView, private var name: String,
        private var fragamentAttachLiestener: FragamentAttachLiestener?
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
            val intent = Intent(context, TermsActivity::class.java)
            context.startActivity(intent)
        }

        @JavascriptInterface
        fun gotoWelcoScreen() {
            fragamentAttachLiestener?.goToWelcomeScreen(name) // Navegue para a tela de boas-vindas
        }

        private fun createWebPrintJob(webView: WebView) {
            val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
            val jobName = "${context.getString(R.string.app_name)} Document"
            val printAdapter = webView.createPrintDocumentAdapter(jobName)
            val printAttributes =
                PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                    .setResolution(PrintAttributes.Resolution("pdf", "pdf", 300, 300))
                    .setMinMargins(PrintAttributes.Margins.NO_MARGINS).build()
            printManager.print(jobName, printAdapter, printAttributes)
        }
    }
}