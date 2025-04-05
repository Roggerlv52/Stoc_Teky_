package com.rogger.myapplication.ui.login.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.rogger.myapplication.R
import com.rogger.myapplication.databinding.FragmentSendSussceBinding
import com.rogger.myapplication.ui.commun.base.DependencyInjector
import com.rogger.myapplication.ui.login.Login
import com.rogger.myapplication.ui.login.presentation.LoginPresenter

class SendSussceFrag : Fragment(R.layout.fragment_send_sussce){
    private var biding : FragmentSendSussceBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        biding = FragmentSendSussceBinding.bind(view)


       Glide.with(this)
           .asGif()
           .load(R.drawable.email_amination)
           .into(biding!!.imgSendSussce)
    }

    override fun onDestroy() {
        super.onDestroy()
        biding = null
    }
}