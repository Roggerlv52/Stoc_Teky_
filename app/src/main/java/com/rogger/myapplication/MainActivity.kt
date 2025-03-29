package com.rogger.myapplication

import com.rogger.myapplication.ui.login.view.LoginActivity
import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rogger.myapplication.ui.avalie.AvalieActivity
import com.rogger.myapplication.ui.commun.util.SharedPrefManager
import com.rogger.myapplication.ui.splashScreen.data.SplashLocalDataSource
import com.rogger.myapplication.ui.terms.TermsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var splashLocalDataSource: SplashLocalDataSource


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        splashLocalDataSource = SplashLocalDataSource(this)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)
        // Define um ícone personalizado

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment_content_main)
        SharedPrefManager.init(this)
        val imageUrl = SharedPrefManager.getString("IMAGE_URL")

        // Infla o layout do cabeçalho
        val headerView: View = navView.getHeaderView(0)

        // Encontra o TextView dentro do cabeçalho
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        if (uid != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val name = document.getString("name")
                        val email = document.getString("email")

                        // Agora atualiza a UI
                        val headerView = navView.getHeaderView(0)
                        val imageViewHeader: ImageView =
                            headerView.findViewById(R.id.imageView_header)
                        val textViewHeader: TextView = headerView.findViewById(R.id.txt_name_reader)
                        val textViewEmail: TextView = headerView.findViewById(R.id.txt_email_reader)
                        textViewHeader.text = "$name"
                        textViewEmail.text = "$email"
                        if (imageUrl != null) {
                            Glide.with(this)         // 'this' pode ser o contexto ou a Activity/Fragment atual
                                .load(imageUrl)      // carrega a imagem a partir da URI
                                .into(imageViewHeader)
                        }
                    } else {
                        Log.d("HeaderInfo", "Documento não encontrado")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("HeaderInfo", "Erro ao buscar usuário", exception)
                }
        }


        drawerLayout = findViewById(R.id.drawer_layout)

        val btnFinish = findViewById<Button>(R.id.drawer_btn_finish)
        btnFinish.setOnClickListener {

            Toast.makeText(this, "Sessão Encerrada!", Toast.LENGTH_SHORT).show()

            splashLocalDataSource.clearSession()
            SharedPrefManager.clearPreferences()
            startActivity(Intent(this, LoginActivity::class.java))
        }
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_helper, R.id.nav_setting, R.id.nav_gestao),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_btn_site -> {
                    // Ação para o item "Site"
                    val url = "https://www.google.com"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                    drawerLayout.closeDrawers()
                    true
                }

                R.id.nav_btn_help -> {
                    openGmail()
                    drawerLayout.closeDrawers()
                    true
                }

                R.id.nav_btn_termos -> {
                    val intent = Intent(this, TermsActivity::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawers()
                    true
                }

                R.id.nav_btn_avaliar -> {
                    val intent = Intent(this, AvalieActivity::class.java)
                    val options = ActivityOptions.makeCustomAnimation(
                        this,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                    )
                    startActivity(intent, options.toBundle())


                    drawerLayout.closeDrawers()
                    false
                }

                R.id.nav_btn_sharad -> {
                    // Ação para o item "Compartilhar"
                    shareAppViaOther()
                    drawerLayout.closeDrawers()
                    true
                }

                else -> {
                    // Deixa o NavController lidar com a navegação para outros itens
                    // Isso é crucial para o AppBarConfiguration funcionar corretamente
                    navController.navigate(menuItem.itemId)
                    drawerLayout.closeDrawers()
                    true
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun shareAppViaOther() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Confira este aplicativo incrível: [link para o app]")
        startActivity(Intent.createChooser(intent, "Compartilhar via"))
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openGmail() {
        val i = Intent(Intent.ACTION_VIEW).apply {
            setPackage("com.google.android.gm")
            data = Uri.parse("mailto:suporte@stocteky.com")
            putExtra(Intent.EXTRA_SUBJECT, "Assunto do e-mail")
            putExtra(Intent.EXTRA_TEXT, "Conteúdo do e-mail")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
        try {
            this.startActivity(i)
        } catch (e: Exception) {
            // Gmail não está instalado
            Toast.makeText(this, "Gmail não está instalado.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // Outro erro inesperado
            Toast.makeText(this, "Ocorreu um erro ao abrir o Gmail.", Toast.LENGTH_SHORT).show()
            e.printStackTrace() // Imprime o erro no log para depuração
        }
    }
}