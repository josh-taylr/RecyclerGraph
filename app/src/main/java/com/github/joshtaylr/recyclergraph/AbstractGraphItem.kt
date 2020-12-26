package com.github.joshtaylr.recyclergraph

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

abstract class AbstractGraphItem : ConstraintLayout, GraphItem {

    abstract val barPixelDimensions: Pair<Int, Int>
    abstract var labelText: CharSequence
    abstract var value: Int
    abstract override var scale: Int

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
}