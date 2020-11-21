package com.github.joshtaylr.recyclergraph

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun withLabel(text: String): Matcher<SimpleDataAdapter.ViewHolder> =
    object : TypeSafeMatcher<SimpleDataAdapter.ViewHolder>() {

        override fun describeTo(description: Description) {
            description.appendText("item with label $text")
        }

        override fun matchesSafely(viewHolder: SimpleDataAdapter.ViewHolder): Boolean {
            return viewHolder.label.text == text
        }
    }
