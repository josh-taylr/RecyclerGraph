package com.github.joshtaylr.recyclergraph.items

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.properties.Delegates.observable

class VerticalBarGraphItemView : View {

    val barHeight: Int
        get() = rect.height().toInt()

    val barWidth: Int
        get() = rect.width().toInt()

    var value: Int by observable(0) { _, _, _ -> invalidate() }
    var scale: Int by observable(0) { _, _, _ -> invalidate() }

    private val paint = Paint()
    private val rect = RectF()

    private val barRatio: Float
        get() = (value.toFloat() / scale).coerceIn(0f, 1f)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        paint.apply {
            color = Color.parseColor("#9C27B0")
        }
    }

    override fun onDraw(canvas: Canvas?) {
        rect.set(0f, 0f, width * barRatio, height.toFloat())
        canvas?.drawRect(rect, paint)
    }
}