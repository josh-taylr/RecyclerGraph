package com.github.joshtaylr.recyclergraph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.joshtaylr.recyclergraph.databinding.ActivityMainBinding
import com.github.joshtaylr.recyclergraph.databinding.SimpleListItemBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val simpleDataAdapter by lazy {
        SimpleDataAdapter(intent.getIntExtra(GraphScale, 26))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        simpleDataAdapter.submitList(SimpleData.create())
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
        holder.scale = scale
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<SimpleData>() {
        override fun areItemsTheSame(oldItem: SimpleData, newItem: SimpleData) = oldItem.x == newItem.x
        override fun areContentsTheSame(oldItem: SimpleData, newItem: SimpleData) = oldItem == newItem
    }

    class ViewHolder private constructor(
        bindings: SimpleListItemBinding
    ) : RecyclerView.ViewHolder(bindings.root) {

        val label = bindings.label
        val value = bindings.value
        var scale = 0

        fun bind(data: SimpleData) {
            label.text = data.x
            value.text = "${data.y} / $scale"
        }

        companion object {
            fun newInstance(parent: ViewGroup) = ViewHolder(
                SimpleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}

data class SimpleData(val x: String, val y: Int) {
    companion object {

        fun create() = createSequence().take(26).toList()

        fun createSequence() = sequence {
            var i = 0
            while (i < 26) {
                yield(SimpleData("${'a' + i}", ++i))
            }
        }
    }
}


