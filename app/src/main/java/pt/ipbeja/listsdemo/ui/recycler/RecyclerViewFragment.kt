package pt.ipbeja.listsdemo.ui.recycler

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.ipbeja.listsdemo.ui.utils.TAG
import pt.ipbeja.listsdemo.ui.utils.content
import pt.ipbeja.listsdemo.R
import pt.ipbeja.listsdemo.databinding.FragmentRecyclerviewBinding
import pt.ipbeja.listsdemo.databinding.ListItemBinding

class RecyclerViewFragment : Fragment() {


    private lateinit var adapter: MyAdapter
    private lateinit var binding: FragmentRecyclerviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.adapter = MyAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = with(FragmentRecyclerviewBinding.inflate(inflater)) {
        binding = this
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.scrollBtn.setOnClickListener {
            binding.recyclerview.scrollToPosition(adapter.itemCount-1)
        }
        binding.recyclerview.adapter = adapter

        binding.viewsCountBtn.setOnClickListener {
            val c = binding.viewsCountInput.content
            val count = if(c.isBlank()) 0 else c.toInt()
            addItemsToList(count)
        }
    }

    private fun addItemsToList(count: Int) {
        val strings = List(count) {
            "Item number ${adapter.itemCount + it + 1}"
        }
        adapter.addItems(strings)
    }
}


class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ListItemBinding.bind(view)

    fun bind(data: String) {
        binding.name.text = data
    }
}

class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

    private var viewHolderCounter = 0 // for demo purposes

    private var data : MutableList<String> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addItems(data: List<String>) {
        val start = data.size
        this.data.addAll(data)
        notifyItemRangeInserted(start, data.size)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.i(TAG, "onCreateViewHolder: ViewHolder count: ${++viewHolderCounter}")
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

}