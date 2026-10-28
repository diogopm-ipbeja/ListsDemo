package pt.ipbeja.listsdemo.ui.recycler

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pt.ipbeja.listsdemo.ui.utils.TAG
import pt.ipbeja.listsdemo.ui.utils.content
import pt.ipbeja.listsdemo.databinding.FragmentRecyclerviewBinding
import pt.ipbeja.listsdemo.databinding.ListItemBinding

class RecyclerViewFragment : Fragment() {

    companion object {
        // false -> MyAdapter (manual notifyItem* calls), true -> MyListAdapter (DiffUtil)
        private const val USE_LIST_ADAPTER = false
    }

    private lateinit var adapter: RecyclerView.Adapter<MyViewHolder>

    // The view is destroyed while the fragment stays in the back stack,
    // so the binding must not outlive it.
    private var _binding: FragmentRecyclerviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.adapter = if (USE_LIST_ADAPTER) MyListAdapter() else MyAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = with(FragmentRecyclerviewBinding.inflate(inflater, container, false)) {
        _binding = this
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.scrollBtn.setOnClickListener {
            binding.recyclerview.scrollToPosition(adapter.itemCount-1)
        }
        binding.recyclerview.adapter = adapter

        binding.viewsCountBtn.setOnClickListener {
            val count = binding.viewsCountInput.content.toIntOrNull() ?: 0
            addItemsToList(count)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addItemsToList(count: Int) {
        val strings = List(count) {
            "Item number ${adapter.itemCount + it + 1}"
        }
        when (val a = adapter) {
            is MyAdapter -> a.addItems(strings)
            is MyListAdapter -> a.submitList(a.currentList + strings)
        }
    }
}


class MyViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: String) {
        binding.name.text = data
    }
}

class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

    // for demo purposes: few ViewHolders are created, but binds keep growing as we scroll
    private var viewHolderCounter = 0
    private var bindCounter = 0

    private val data: MutableList<String> = mutableListOf()

    fun addItems(items: List<String>) {
        val start = this.data.size
        this.data.addAll(items)
        // Only tell the RecyclerView what changed. notifyDataSetChanged() would
        // rebind every visible item and skip the insert animations.
        notifyItemRangeInserted(start, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.i(TAG, "onCreateViewHolder: ViewHolder count: ${++viewHolderCounter}")

        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder: bind count: ${++bindCounter}")
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

}

/**
 * Same as [MyAdapter], but ListAdapter keeps the list and uses DiffUtil
 * to work out which notifyItem* calls to make when a new list is submitted.
 */
class MyListAdapter : ListAdapter<String, MyViewHolder>(DIFF_CALLBACK) {

    private var viewHolderCounter = 0 // for demo purposes
    private var bindCounter = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.i(TAG, "onCreateViewHolder: ViewHolder count: ${++viewHolderCounter}")
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder: bind count: ${++bindCounter}")
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
            override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        }
    }
}
