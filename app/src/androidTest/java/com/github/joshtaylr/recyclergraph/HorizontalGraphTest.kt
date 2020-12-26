package com.github.joshtaylr.recyclergraph

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.joshtaylr.recyclergraph.databinding.ActivityMainBinding
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HorizontalGraphTest {

    private val density: Float
        get() = instrumentationContext.resources.displayMetrics.density

    private lateinit var instrumentationContext: Context

    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun drawBarOnVerticalGraph() {
        launchActivity<HorizontalGraphActivity>()

        Espresso.onView(withItemLabel("b")).check(
            ViewAssertions.matches(
                withGraphItemPixelDimensions(
                    width = (48 * density).toInt(),
                    height = (20 * density).toInt()
                )
            )
        )
    }
}

