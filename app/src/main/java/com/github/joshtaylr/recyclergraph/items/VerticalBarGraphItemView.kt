package com.github.joshtaylr.recyclergraph.items

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat.getDrawable
import com.github.joshtaylr.recyclergraph.R
import kotlin.properties.Delegates.observable

class VerticalBarGraphItemView : View {

    val barWidth: Int
        get() = rect.width()

    val barHeight: Int
        get() = rect.height()

    var value: Int by observable(0) { _, _, _ -> invalidate() }
    var scale: Int by observable(0) { _, _, _ -> invalidate() }

    var drawable: Drawable? by observable(null) { _, _, _ -> invalidate() }

    @setparam:DrawableRes
    var drawableRes: Int by observable(-1) { _, _, newRes ->
        drawable = getDrawable(resources, newRes, context.theme)
    }

    private val barRatio: Float
        get() = (value.toFloat() / scale).coerceIn(0f, 1f)

    private val paint = Paint()
    private val rect = Rect()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        paint.apply {
            color = Color.parseColor("#9C27B0")
        }

        drawableRes = R.drawable.rounded_bar_graph_item

        if (isInEditMode) {
            value = 3
            scale = 10
        }
    }

    override fun onDraw(canvas: Canvas?) {

        rect.set(0, 0, (width * barRatio).toInt(), height)

        drawable?.let { drawable ->
            drawable.bounds = rect
            canvas?.let { drawable.draw(it) }
        }
    }
}