package com.github.joshtaylr.recyclergraph

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val density: Float
        get() = instrumentationContext.resources.displayMetrics.density

    private lateinit var instrumentationContext: Context

    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun stacksItemsFromStart() {
        launchActivity<VerticalBarGraphActivity>()

        onView(withText("a")).check(matches(isDisplayed()))
        onView(withText("z")).check(doesNotExist())
    }

    @Test
    fun stacksItemsFromEnd() {

        val intent = Intent(ApplicationProvider.getApplicationContext(), VerticalBarGraphActivity::class.java)

        intent.putExtras(
            bundleOf(
                VerticalBarGraphActivity.StackGraphFromEnd to true
            )
        )

        launchActivity<VerticalBarGraphActivity>(intent)

        onView(withText("u")).check(matches(isDisplayed()))
        onView(withText("a")).check(doesNotExist())
    }

    @Test
    fun showItemValue() {
        launchActivity<VerticalBarGraphActivity>()

        onView(allOf(withChild(withText("c")), withChild(withText("2"))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun scrollToItemOutOfView() {
        launchActivity<VerticalBarGraphActivity>()

        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.scrollToHolder(withLabel("m")))

        onView(withText("12")).check(matches(isDisplayed()))
    }

    @Test
    fun updateGraphScale() {
        val scenario = launchActivity<VerticalBarGraphActivity>()

        onView(withItemLabel("a")).check(matches(withItemScale(20)))

        scenario.onActivity {
            it.setGraphScale(30)
        }

        onView(withItemLabel("a")).check(matches(withItemScale(30)))
    }

    @Test
    fun drawZeroWithCorrectDimensions() {
        launchActivity<VerticalBarGraphActivity>()

        onView(withItemLabel("a")).check(matches(withGraphItemPixelDimensions(
            width = 0,
            height = (48 * density).toInt()
        )))
    }

    @Test
    fun drawSmallBarToScale() {
        launchActivity<VerticalBarGraphActivity>()

        onView(withItemLabel("b")).check(matches(withGraphItemPixelDimensions(
            width = (7.6 * density).toInt(),
            height = (48 * density).toInt()
        )))
    }

    @Test
    fun drawLargeBarToScale() {
        launchActivity<VerticalBarGraphActivity>()

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToHolder(withLabel("u")))

        onView(withItemLabel("u")).check(matches(withGraphItemPixelDimensions(
            width = (152 * density).toInt(),
            height = (48 * density).toInt()
        )))
    }
}