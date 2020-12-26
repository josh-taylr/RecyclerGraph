package com.github.joshtaylr.recyclergraph

import com.github.joshtaylr.recyclergraph.data.SimpleData
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class SimpleDataTest {

    @Test
    fun addition_isCorrect() {

        val result = SimpleData.createSequence().take(3).toList()

        assertThat(result, CoreMatchers.hasItems(
            SimpleData("a", 1),
            SimpleData("b", 2),
            SimpleData("c", 3),
        ))
    }
}