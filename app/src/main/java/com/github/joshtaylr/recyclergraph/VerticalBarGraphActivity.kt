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
import com.github.joshtaylr.recyclergraph.data.SimpleData
import com.github.joshtaylr.recyclergraph.databinding.ActivityMainBinding
import com.github.joshtaylr.recyclergraph.items.VerticalBarGraphItem

class VerticalBarGraphActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val simpleDataAdapter by lazy {
        VerticalBarGraphAdapter(intent.getIntExtra(GraphScale, 20))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        simpleDataAdapter.submitList(SimpleData.create(21))
        binding.recyclerView.adapter = simpleDataAdapter

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = intent.getBooleanExtra(StackGraphFromEnd, false)
        linearLayoutManager.orientation = if (intent.getBooleanExtra(HorizontalGraph, false)) {
            RecyclerView.HORIZONTAL
        } else {
            RecyclerView.VERTICAL
        }
        binding.recyclerView.layoutManager = linearLayoutManager
    }

    fun setGraphScale(value: Int) {
        simpleDataAdapter.scale = value
        simpleDataAdapter.notifyDataSetChanged()
    }

    companion object {
        const val HorizontalGraph: String = "VerticalGraph"
        const val StackGraphFromEnd = "ReverseGraphLayout"
        const val GraphScale = "GraphScale"
    }
}

class VerticalBarGraphAdapter(var scale: Int) : ListAdapter<SimpleData, VerticalBarGraphAdapter.ViewHolder>(DiffCallback) {

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
        val graphItem: VerticalBarGraphItem
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
                VerticalBarGraphItem(parent.context).apply {
                    layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                }
            )
        }
    }
}


