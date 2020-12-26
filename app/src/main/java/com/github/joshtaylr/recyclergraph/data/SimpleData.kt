package com.github.joshtaylr.recyclergraph.data

data class SimpleData(val x: String, val y: Int) {
    companion object {

        fun create(n: Int) = generateSequence().take(n).toList()

        private fun generateSequence() = sequence {
            var i = 0
            while (i < 26) {
                yield(SimpleData("${'a' + i}", i))
                i++
            }
        }

        fun create25s() = listOf(
            SimpleData("a", 0),
            SimpleData("b", 25),
            SimpleData("c", 50),
            SimpleData("d", 75),
            SimpleData("e", 100),
            SimpleData("f", 125),
            SimpleData("g", 150),
            SimpleData("h", 175),
            SimpleData("i", 200),
        )
    }
}