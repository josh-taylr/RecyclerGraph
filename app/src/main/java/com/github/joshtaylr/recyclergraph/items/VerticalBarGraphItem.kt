package com.github.joshtaylr.recyclergraph.items

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.github.joshtaylr.recyclergraph.R
import com.github.joshtaylr.recyclergraph.databinding.VerticalBarGraphItemBinding

class VerticalBarGraphItem : AbstractBarGraphItem {

    override val barPixelDimensions: Pair<Int, Int>
        get() = bindings.barItem.barWidth to bindings.barItem.barHeight

    override var labelText: CharSequence
        get() = bindings.label.text.toString()
        set(value) {
            bindings.label.text = value
        }

    override var value: Int
        get() = bindings.barItem.value
        set(value) {
            bindings.value.text = "$value"
            bindings.barItem.value = value
        }

    override var scale: Int = 0
        set(value) {
            field = value
            bindings.barItem.scale = scale
        }

    private val bindings by lazy {
        VerticalBarGraphItemBinding.bind(this)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        LayoutInflater.from(context).inflate(R.layout.vertical_bar_graph_item, this, true)
    }
}