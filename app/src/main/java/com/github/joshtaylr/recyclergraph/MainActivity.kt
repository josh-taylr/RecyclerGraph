package com.github.joshtaylr.recyclergraph

import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.joshtaylr.recyclergraph.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val simpleDataAdapter by lazy {
        SimpleDataAdapter(intent.getIntExtra(GraphScale, 26))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        simpleDataAdapter.submitList(SimpleData.create(26))
        binding.recyclerView.adapter = simpleDataAdapter

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = intent.getBooleanExtra(StackGraphFromEnd, false)
        binding.recyclerView.layoutManager = linearLayoutManager
    }

    fun setGraphScale(value: Int) {
        simpleDataAdapter.scale = value
        simpleDataAdapter.notifyDataSetChanged()
    }

    companion object {
        const val StackGraphFromEnd = "ReverseGraphLayout"
        const val GraphScale = "GraphScale"
    }
}

class SimpleDataAdapter(var scale: Int) : ListAdapter<SimpleData, SimpleDataAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.newInstance(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateScale(scale)
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<SimpleData>() {
        override fun areItemsTheSame(oldItem: SimpleData, newItem: SimpleData) = oldItem.x == newItem.x
        override fun areContentsTheSame(oldItem: SimpleData, newItem: SimpleData) = oldItem == newItem
    }

    class ViewHolder private constructor(
        val graphItem: SimpleGraphItem
    ) : RecyclerView.ViewHolder(graphItem) {

        fun bind(data: SimpleData) {
            graphItem.labelText = data.x
            graphItem.value = data.y
        }

        fun updateScale(value: Int) {
            graphItem.scale = value
        }

        companion object {
            fun newInstance(parent: ViewGroup) = ViewHolder(
                SimpleGraphItem(parent.context).apply {
                    layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                }
            )
        }
    }
}

data class SimpleData(val x: String, val y: Int) {
    companion object {

        fun create(n: Int) = generateSequence().take(n).toList()

        private fun generateSequence() = sequence {
            var i = 0
            while (i < 26) {
                yield(SimpleData("${'a' + i}", ++i))
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


