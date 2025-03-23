package com.rogger.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.rogger.myapplication.ui.terms.TermsActivity
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var doubleBackToExitPressedOnce = false
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // installSplashScreen()
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        //toolbar.setBackgroundColor(android.graphics.Color.TRANSPARENT)
        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)
        // Define um ícone personalizado

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment_content_main)

        // Infla o layout do cabeçalho
        val headerView: View = navView.getHeaderView(0)

        // Encontra o TextView dentro do cabeçalho
        val textViewHeader: TextView = headerView.findViewById(R.id.txt_name_reader)
        // Agora você pode usar o textViewHeader
        textViewHeader.text = "Nome do usuario aqui"

        drawerLayout = findViewById(R.id.drawer_layout)

        val btnFinish = findViewById<Button>(R.id.drawer_btn_finish)
        var cont = 0
        btnFinish.setOnClickListener {
            cont++
            Toast.makeText(this, "Clique mais uma vez para sair ", Toast.LENGTH_SHORT).show()
            if (cont == 2) {
                Toast.makeText(this, "Sessão Encerrada!", Toast.LENGTH_SHORT).show()
                finishAffinity()
            }
        }
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_helper, R.id.nav_setting),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Configura o callback para o botão de voltar
        screenFinish()
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
                    // Ação para o item "Avaliar"
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

    private fun screenFinish() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    finishAffinity() // Encerra todas as atividades do aplicativo
                    return
                }

                doubleBackToExitPressedOnce = true
                Toast.makeText(
                    this@MainActivity,
                    "Clique mais uma vez para sair ",
                    Toast.LENGTH_SHORT
                ).show()

                Handler(Looper.getMainLooper()).postDelayed({
                    doubleBackToExitPressedOnce = false
                }, 2000) // Tempo de espera de 2 segundos
            }
        })
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