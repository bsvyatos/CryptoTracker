package com.example.cryptotracker.utils

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.withStyledAttributes
import com.example.cryptotracker.R

class ColoredChangeTextView(context: Context, attrs: AttributeSet): AppCompatTextView(context, attrs) {
    private var minusColor = Color.RED
    private var plusColor = Color.GREEN
    private var maxColor = 1

    init {
        context.withStyledAttributes(attrs, R.styleable.ColoredChangeTextView) {
            minusColor = getColor(R.styleable.ColoredChangeTextView_minusColor, minusColor)
            plusColor = getColor(R.styleable.ColoredChangeTextView_plusColor, plusColor)
            maxColor = getInt(R.styleable.ColoredChangeTextView_maxColorValue, maxColor)
        }
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