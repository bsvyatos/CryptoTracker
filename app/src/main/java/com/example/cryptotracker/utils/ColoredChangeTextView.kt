package com.example.cryptotracker.utils

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.withStyledAttributes
import com.example.cryptotracker.R

class ColoredChangeTextView(context: Context, attrs: AttributeSet) :
    AppCompatTextView(context, attrs) {
    private var minusColor = Color.RED
    private var plusColor = Color.GREEN
    private var maxColor = 10

    init {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColoredChangeTextView)
        minusColor = typedArray.getColor(R.styleable.ColoredChangeTextView_minusColor, minusColor)
        plusColor = typedArray.getColor(R.styleable.ColoredChangeTextView_plusColor, plusColor)
        maxColor = typedArray.getInt(R.styleable.ColoredChangeTextView_maxColorValue, maxColor)
        typedArray.recycle()
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        text?.let {
            Utils.getFloat(it)
        }?.let {
            this.setTextColor(Utils.getChangeTextColor(minusColor, plusColor, maxColor, it))
        }
    }
}