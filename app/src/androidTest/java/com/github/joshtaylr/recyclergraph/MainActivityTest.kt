package com.github.joshtaylr.recyclergraph

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
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun stacksItemsFromStart() {
        launchActivity<MainActivity>()

        onView(withText("a")).check(matches(isDisplayed()))
        onView(withText("z")).check(doesNotExist())
    }

    @Test
    fun stacksItemsFromEnd() {

        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)

        intent.putExtras(
            bundleOf(
                MainActivity.StackGraphFromEnd to true
            )
        )

        launchActivity<MainActivity>(intent, bundleOf())

        onView(withText("z")).check(matches(isDisplayed()))
        onView(withText("a")).check(doesNotExist())
    }

    @Test
    fun showItemValue() {
        launchActivity<MainActivity>()

        onView(allOf(withChild(withText("c")), withChild(withText("3"))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun scrollToItemOutOfView() {
        launchActivity<MainActivity>()

        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.scrollToHolder(withLabel("m")))

        onView(withText("13")).check(matches(isDisplayed()))
    }

    @Test
    fun updateGraphScale() {
        val scenario = launchActivity<MainActivity>()

        onView(withItemLabel("a")).check(matches(withItemScale(26)))

        scenario.onActivity {
            it.setGraphScale(30)
        }

        onView(withItemLabel("a")).check(matches(withItemScale(30)))
    }

    @Test
    fun drawSmallBarToScale() {
        launchActivity<MainActivity>()

        val density = InstrumentationRegistry.getInstrumentation().context.resources.displayMetrics.density
        onView(withItemLabel("a")).check(matches(withGraphItemPixelMeasurement((1f / 26 * 200 * density).toInt())))
    }

    @Test
    fun drawLargeBarToScale() {
        launchActivity<MainActivity>()

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToHolder(withLabel("z")))

        val density = InstrumentationRegistry.getInstrumentation().context.resources.displayMetrics.density

        onView(withItemLabel("z")).check(matches(withGraphItemPixelMeasurement((200 * density).toInt())))
    }
}