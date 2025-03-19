package com.rogger.myapplication.ui.commun.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.rogger.myapplication.R

class LoadingButton : FrameLayout {
    private lateinit var button: Button
    private lateinit var progress: ProgressBar
    private var defaultText: String? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setup(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStylerAttr: Int) : super(
        context, attrs, defStylerAttr
    ) {
        setup(context, attrs)
    }

    private fun setup(context: Context, attrs: AttributeSet?) {
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.bottom_loading, this,true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0, 0)
        defaultText = typedArray.getString(R.styleable.LoadingButton_text)?: "Login"

        typedArray.recycle()

        button = getChildAt(0) as Button
        progress = getChildAt(1) as ProgressBar
        button.text = defaultText
        button.setTextColor(ContextCompat.getColor(context, android.R.color.white)) // Usar ContextCompat
        button.height =50
        button.isAllCaps=false

        // Definir o estado inicial
        showProgress(false)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        button.isEnabled = enabled
        if (!enabled) {
            button.alpha = 0.5f // Dica visual para o estado desativado
            //button.setBackgroundResource(R.drawable.button_backgraund_disabled)
        } else {
            button.alpha = 1.0f
           // button.setBackgroundResource(R.drawable.button_backgraund_enabled)
        }
    }
    override fun setOnClickListener(l: OnClickListener?) {
        button.setOnClickListener(l)
    }

    fun showProgress(enabled: Boolean) {
        if (enabled) {
            button.text = ""
            button.isEnabled = false
            progress.visibility = View.VISIBLE
            button.alpha = 0.5f
            //button.setBackgroundResource(R.drawable.button_backgraund_disabled)
        } else {
            button.text = defaultText
            button.isEnabled = true
            progress.visibility = View.GONE
            button.alpha = 1.0f
            //button.setBackgroundResource(R.drawable.button_backgraund_enabled)
        }
    }

}