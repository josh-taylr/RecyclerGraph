package com.github.joshtaylr.recyclergraph

import android.view.View
import androidx.annotation.Px
import androidx.test.espresso.matcher.BoundedMatcher
import com.github.joshtaylr.recyclergraph.items.AbstractBarGraphItem
import com.github.joshtaylr.recyclergraph.items.GraphItem
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher

fun withLabel(text: String): Matcher<VerticalBarGraphAdapter.ViewHolder> =
    object : TypeSafeMatcher<VerticalBarGraphAdapter.ViewHolder>() {

        override fun describeTo(description: Description) {
            description.appendText("item with label $text")
        }

        override fun matchesSafely(viewHolder: VerticalBarGraphAdapter.ViewHolder): Boolean {
            return viewHolder.graphItem.labelText == text
        }
    }

fun withItemLabel(text: String): Matcher<View> = withItemLabel(Matchers.`is`(text))

fun withItemLabel(stringMatcher: Matcher<String>): Matcher<View> {
    return WithItemLabelMatcher(stringMatcher)
}

fun withItemScale(value: Int): Matcher<View> {
    return WithItemScaleMatcher(value)
}

class WithItemLabelMatcher(
    private val stringMatcher: Matcher<String>
) : BoundedMatcher<View, AbstractBarGraphItem>(AbstractBarGraphItem::class.java) {

    override fun describeTo(description: Description) {
        description.appendText("with graph label ")
        stringMatcher.describeTo(description)
    }

    override fun matchesSafely(item: AbstractBarGraphItem) = stringMatcher.matches(item.labelText)
}

class WithItemScaleMatcher(
    private val value: Int
) : BoundedMatcher<View, AbstractBarGraphItem>(AbstractBarGraphItem::class.java) {

    override fun describeTo(description: Description) {
        description.appendText("with graph scale ")
        description.appendValue(value)
    }

    override fun matchesSafely(item: AbstractBarGraphItem) = (item as GraphItem).scale == value
}

fun withGraphItemPixelDimensions(@Px width: Int? = null, @Px height: Int? = null): Matcher<View> {
    return WithGraphItemBarDimensionsMatcher(width, height)
}

class WithGraphItemBarDimensionsMatcher(
    @Px private val width: Int?,
    @Px private val height: Int?
) : BoundedMatcher<View, AbstractBarGraphItem>(AbstractBarGraphItem::class.java) {

    override fun describeTo(description: Description) {
        description.appendText("with graph item pixel width ")
        description.appendValue(width ?: "Any")
        description.appendText(" height ")
        description.appendValue(height ?: "Any")
    }

    override fun matchesSafely(item: AbstractBarGraphItem): Boolean {
        val dimensions = item.barPixelDimensions
        return (width == null || width == dimensions.first) && (height == null || height == dimensions.second)
    }
}