package com.rogger.myapplication.ui.login.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.rogger.myapplication.MainActivity
import com.rogger.myapplication.R
import com.rogger.myapplication.databinding.ActivityLoginBinding
import com.rogger.myapplication.ui.commun.BaseActivit
import com.rogger.myapplication.ui.commun.base.DependencyInjector
import com.rogger.myapplication.ui.commun.util.SharedPrefManager
import com.rogger.myapplication.ui.commun.util.TxtWatcher
import com.rogger.myapplication.ui.login.Login
import com.rogger.myapplication.ui.register.view.RegisterActivity
import com.rogger.myapplication.ui.splashScreen.data.SplashLocalDataSource

class LoginActivity : BaseActivit(), Login.View {

    private val RC_ONE_TAP = 2
    private var binding: ActivityLoginBinding? = null
    private lateinit var oneTapClient: SignInClient
    private var name: String? = null
    private var email: String? = null
    private var uri: Uri? = null

    override lateinit var presenter: Login.Presenter

    private val oneTapRequest: BeginSignInRequest by lazy {
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SharedPrefManager.init(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.loginBtnEnter?.isEnabled = false
        presenter = DependencyInjector.loginPresenter(this, this)

        binding?.let { viewBinding ->
            with(viewBinding) {
                loginEditEmail.addTextChangedListener(watcher)
                loginEditEmail.addTextChangedListener(TxtWatcher { displayEmailFailure(null) })
                loginEditPassword.addTextChangedListener(watcher)
                loginEditPassword.addTextChangedListener(TxtWatcher { displayPasswordFailure(null) })

                loginBtnEnter.setOnClickListener {
                    presenter.login(
                        loginEditEmail.text.toString(),
                        loginEditPassword.text.toString()
                    )
                }
                btnGoogleLogin.setOnClickListener {
                    startOneTapSignIn()
                }
                loginTxtRegister.setOnClickListener {
                    goToRegisterScreen()
                }
                // Alternar visibilidade da senha e trocar ícones
                loginEditPasswordInput.setEndIconOnClickListener {
                    val isPasswordVisible =
                        loginEditPassword.inputType == android.text.InputType.TYPE_CLASS_TEXT
                    if (isPasswordVisible) {
                        loginEditPassword.inputType =
                            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                        loginEditPasswordInput.endIconDrawable = ContextCompat.getDrawable(
                            this@LoginActivity,
                            R.drawable.ic_visibility_off_24
                        )
                    } else {
                        loginEditPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT
                        loginEditPasswordInput.endIconDrawable = ContextCompat.getDrawable(
                            this@LoginActivity,
                            R.drawable.ic_visibility_24
                        )
                    }
                    loginEditPassword.setSelection(loginEditPassword.text?.length ?: 0)
                }
            }
        }
    }

    fun startOneTapSignIn() {
        oneTapClient = Identity.getSignInClient(this)
        oneTapClient.beginSignIn(oneTapRequest)
            .addOnSuccessListener { result ->
                try {
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender,
                        RC_ONE_TAP,
                        null,
                        0,
                        0,
                        0
                    )
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("LoginActivity", "Erro ao iniciar One Tap: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener { e ->
                Log.e("LoginActivity", "Falha ao iniciar One Tap: ${e.localizedMessage}")
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_ONE_TAP) {
            try {
                val credential: SignInCredential = oneTapClient.getSignInCredentialFromIntent(data)
                val idToken = credential.googleIdToken
                if (idToken != null) {
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(firebaseCredential)
                        .addOnCompleteListener { authTask ->
                            if (authTask.isSuccessful) {
                                val user = authTask.result.user
                                uri = user?.photoUrl
                                SharedPrefManager.saveString("IMAGE_URL", uri?.toString().orEmpty())
                                name = user?.displayName
                                email = user?.email

                                // Verifica se o documento do usuário existe no Firestore
                                FirebaseFirestore.getInstance().collection("/users")
                                    .document(user!!.uid)
                                    .get()
                                    .addOnSuccessListener { document ->
                                        if (document.exists()) {
                                            // Usuário já criou a conta anteriormente
                                            onUserAuthenticated() // Vai para MainActivity
                                        } else {
                                            // Documento não existe; usuário novo ou não completou cadastro
                                            goToRegisterScreen() // Vai para RegisterActivity
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.e("LoginActivity", "Erro ao verificar documento: ${exception.message}")
                                        // Se ocorrer erro, podemos optar por direcionar o usuário para completar o cadastro
                                        goToRegisterScreen()
                                    }
                            } else {
                                Log.e("LoginActivity", "Falha no login Firebase", authTask.exception)
                            }
                        }
                } else {
                    Toast.makeText(this, "Token de ID não encontrado!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Erro ao recuperar credencial do One Tap: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                Log.e("LoginActivity", "Erro ao recuperar credencial do One Tap: ${e.localizedMessage}", e)
            }
        }
    }

    private val watcher = TxtWatcher {
        val isEmailFilled = binding?.loginEditEmail?.text.toString().isNotEmpty()
        val isPasswordFilled = binding?.loginEditPassword?.text.toString().isNotEmpty()
        binding?.loginBtnEnter?.isEnabled = isEmailFilled && isPasswordFilled
    }

    private fun goToRegisterScreen() {
        SplashLocalDataSource(this).setLoggedIn(true)
        val intent = Intent(this, RegisterActivity::class.java).apply {
            putExtra("EXTRA_NAME", name)
            putExtra("EXTRA_EMAIL", email)
        }
        startActivity(intent)
    }

    override fun onUserAuthenticated() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    override fun onUserUnauthorized(message: String) {
        binding?.progressbarLogin?.visibility = View.GONE
    }

    override fun showProgress(enabled: Boolean) {
        binding?.progressbarLogin?.visibility = if (enabled) View.VISIBLE else View.INVISIBLE
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding?.progressbarLogin?.visibility = View.GONE
        binding?.loginEditEmailInput?.error = emailError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding?.progressbarLogin?.visibility = View.GONE
        binding?.loginEditPasswordInput?.error = passwordError?.let { getString(it) }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}
