package com.example.disputer.training.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.disputer.R
import com.example.disputer.databinding.CustomArrowButtonBinding

class ArrowButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: CustomArrowButtonBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = CustomArrowButtonBinding.inflate(inflater, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ArrowButton)
            val text = typedArray.getString(R.styleable.ArrowButton_buttonText) ?: ""
            if (typedArray.hasValue(R.styleable.ArrowButton_backgroundColor)) {
                val bgColor = typedArray.getColor(R.styleable.ArrowButton_backgroundColor, 0)
                binding.myButtonContainer.setBackgroundColor(bgColor)
            }

            binding.tabNameTV.text = text

            typedArray.recycle()
        }


    }

//    private fun applyRippleEffect() {
//        isClickable = true
//        isFocusable = true
//
//        val outValue = TypedValue()
//        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
//        binding.myButtonContainer.foreground = ContextCompat.getDrawable(context, outValue.resourceId)
//    }
}