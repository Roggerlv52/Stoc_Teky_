package com.rogger.myapplication.ui.login.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.rogger.myapplication.R
import com.rogger.myapplication.ui.commun.extension.hidekyboard

class ResetPasswordActivity : AppCompatActivity(),FragmentAttch {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        val toolbar = findViewById<Toolbar>(R.id.tb_send)
        toolbar.setTitle("")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            replaceFragment(ResetEnterEmailFragment())
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
                finish() // Finaliza a Activity atual
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if (supportFragmentManager.findFragmentById(R.id.send_nav_fragment) == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.send_nav_fragment, fragment)
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.send_nav_fragment, fragment)
                addToBackStack(null)
                commit()
            }
        }
        hidekyboard()
    }

    override fun sussce() {
        val fragment = SendSussceFrag().apply {
            arguments = Bundle().apply {
            }
        }
        replaceFragment(fragment)
    }
}