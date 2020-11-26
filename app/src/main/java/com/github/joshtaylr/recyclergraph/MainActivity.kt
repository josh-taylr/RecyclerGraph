package com.github.joshtaylr.recyclergraph

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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

interface GraphItem {
    val scale: Int
}

class SimpleGraphItem : ConstraintLayout, GraphItem {

    var labelText: CharSequence
        get() = bindings.label.text.toString()
        set(value) {
            bindings.label.text = value
        }

    var value: Int
        get() = bindings.value.text.toString().toIntOrNull() ?: 0
        set(value) {
            bindings.value.text = "$value"
        }

    override var scale: Int = 0

    private val bindings by lazy {
        SimpleListItemBinding.bind(this)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        LayoutInflater.from(context).inflate(R.layout.simple_list_item, this, true)
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


