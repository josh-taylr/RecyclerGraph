package com.github.joshtaylr.recyclergraph

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.joshtaylr.recyclergraph.data.SimpleData
import com.github.joshtaylr.recyclergraph.databinding.ActivityMainBinding
import com.github.joshtaylr.recyclergraph.items.HorizontalBarGraphItem

class HorizontalGraphActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val simpleDataAdapter by lazy {
        HorizontalDataAdapter(20)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        simpleDataAdapter.submitList(SimpleData.create(21))
        binding.recyclerView.adapter = simpleDataAdapter

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL

        binding.recyclerView.layoutManager = linearLayoutManager
    }
}

private class HorizontalDataAdapter(var scale: Int) : ListAdapter<SimpleData, HorizontalDataAdapter.ViewHolder>(DiffCallback) {

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
        val graphItem: HorizontalBarGraphItem
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
                HorizontalBarGraphItem(parent.context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            )
        }
    }
}