package com.github.joshtaylr.recyclergraph

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.joshtaylr.recyclergraph.databinding.SimpleListItemBinding

class SimpleGraphItem : ConstraintLayout, GraphItem {

    var labelText: CharSequence
        get() = bindings.label.text.toString()
        set(value) {
            bindings.label.text = value
        }

    var value: Int
        get() = bindings.value.text.toString().toIntOrNull() ?: 0
        set(value) {
            bindings.value.text = "$value"
        }

    override var scale: Int = 0

    private val bindings by lazy {
        SimpleListItemBinding.bind(this)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        LayoutInflater.from(context).inflate(R.layout.simple_list_item, this, true)
    }
}