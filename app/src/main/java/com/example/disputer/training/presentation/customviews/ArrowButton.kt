package com.example.disputer.training.presentation.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.disputer.R
import com.example.disputer.databinding.CustomArrowButtonBinding
import androidx.core.content.withStyledAttributes


class ArrowButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatButton(context, attrs, defStyleAttr) {

    private lateinit var textView: TextView
    private lateinit var imageView: ImageView

    init {
        background = null
        setPadding(0, 0, 0, 0)

        val container = ConstraintLayout(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            setPadding(
                resources.getDimensionPixelSize(R.dimen.button_padding_horizontal),
                resources.getDimensionPixelSize(R.dimen.button_padding_vertical),
                resources.getDimensionPixelSize(R.dimen.button_padding_horizontal),
                resources.getDimensionPixelSize(R.dimen.button_padding_vertical)
            )
            background = ContextCompat.getDrawable(context, R.drawable.rounded_bg)
        }

        // Создаем TextView
        textView = TextView(context).apply {
            id = View.generateViewId()
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                marginStart = resources.getDimensionPixelSize(R.dimen.button_text_margin_start)
            }
            setTextAppearance(R.style.TextViewMainStyle)
            maxLines = 1
            setPadding(
                resources.getDimensionPixelSize(R.dimen.button_text_padding),
                resources.getDimensionPixelSize(R.dimen.button_text_padding),
                resources.getDimensionPixelSize(R.dimen.button_text_padding),
                resources.getDimensionPixelSize(R.dimen.button_text_padding)
            )
        }

        // Создаем ImageView
        imageView = ImageView(context).apply {
            id = View.generateViewId()
            layoutParams = ConstraintLayout.LayoutParams(
                resources.getDimensionPixelSize(R.dimen.button_icon_size),
                resources.getDimensionPixelSize(R.dimen.button_icon_size)
            ).apply {
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                marginEnd = resources.getDimensionPixelSize(R.dimen.button_icon_margin_end)
            }
            scaleType = ImageView.ScaleType.FIT_CENTER
            setImageResource(R.drawable.next)
            background = ContextCompat.getDrawable(context, R.drawable.rounded_white_button_bg)
        }

        // Добавляем вьюхи в контейнер
        container.addView(textView)
        container.addView(imageView)

        // Добавляем контейнер в кнопку
        //addView(container)

        // Обрабатываем атрибуты
        attrs?.let {
            context.withStyledAttributes(it, R.styleable.ArrowButton) {
                val text = getString(R.styleable.ArrowButton_buttonText) ?: ""
                if (hasValue(R.styleable.ArrowButton_backgroundColor)) {
                    val bgColor = getColor(R.styleable.ArrowButton_backgroundColor, 0)
                    container.background?.mutate()?.setTint(bgColor)
                }
                textView.text = text
            }
        }
    }

}