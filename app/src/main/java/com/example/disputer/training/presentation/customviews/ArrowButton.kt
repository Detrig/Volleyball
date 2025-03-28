package com.example.disputer.training.presentation.customviews

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.disputer.R

class ArrowButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val textView: TextView
    private val imageButton: ImageButton

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_arrow_button, this, true)

        textView = findViewById(R.id.textView)
        imageButton = findViewById(R.id.myTrainingsButton)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArrowButton)
        try {
            textView.text = typedArray.getString(R.styleable.ArrowButton_buttonText)
            textView.setTextColor(typedArray.getColor(R.styleable.ArrowButton_textColor, Color.WHITE))
            imageButton.setImageDrawable(typedArray.getDrawable(R.styleable.ArrowButton_buttonIcon))
            this.rootView.setBackgroundColor(R.styleable.ArrowButton_backgroundColor)
        } finally {
            typedArray.recycle()
        }
    }

    fun setOnButtonClickListener(listener: OnClickListener) {
        imageButton.setOnClickListener(listener)
        setOnClickListener(listener)
    }
}