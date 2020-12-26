package com.github.joshtaylr.recyclergraph

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.github.joshtaylr.recyclergraph.databinding.HorizontalGraphItemBinding
import com.github.joshtaylr.recyclergraph.databinding.SimpleListItemBinding
import kotlin.math.min
import kotlin.math.nextDown

class HorizontalGraphItem : AbstractGraphItem {

    override val barPixelDimensions: Pair<Int, Int>
        get() = bindings.barView.measuredWidth to bindings.barView.measuredHeight

    override var labelText: CharSequence
        get() = bindings.label.text.toString()
        set(value) {
            bindings.label.text = value
        }

    override var value: Int
        get() = bindings.value.text.toString().toIntOrNull() ?: 0
        set(value) {
            bindings.value.text = "$value"
            updateBarWidth()
        }

    override var scale: Int = 0
        set(value) {
            field = value
            updateBarWidth()
        }

    private val bindings by lazy {
        HorizontalGraphItemBinding.bind(this)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        LayoutInflater.from(context).inflate(R.layout.horizontal_graph_item, this, true)
    }

    private fun updateBarWidth() {
        ConstraintSet().apply {
            clone(this@HorizontalGraphItem)
            var ratio = value / scale.toFloat()
            ratio = min(1f, ratio)
            ratio = ratio.nextDown()
            constrainPercentHeight(R.id.barView, ratio)
        }.also {
            it.applyTo(this)
        }
    }
}