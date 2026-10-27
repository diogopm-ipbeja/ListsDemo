package pt.ipbeja.listsdemo.ui.scrollview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


import pt.ipbeja.listsdemo.ui.utils.content
import pt.ipbeja.listsdemo.databinding.FragmentScrollingBinding
import kotlin.time.measureTime

class ScrollingFragment : Fragment() {

    private var _binding: FragmentScrollingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = with(FragmentScrollingBinding.inflate(inflater, container, false)) {
        _binding = this
        root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.viewsCountBtn.setOnClickListener {
            val count = binding.viewsCountInput.content.toIntOrNull() ?: 0

            setViews(count)
        }
    }

    private fun setViews(count: Int) {
        binding.container.removeAllViews()

        val strings = List(count) { "Textview #$it" }
        val elapsedTime = measureTime {

            repeat(count) {
                binding.container.addView(TextView(requireContext()).apply {
                    text = strings[it]
                })
            }
        }

        Toast.makeText(
            requireContext(),
            "Elapsed time: ${elapsedTime.inWholeMilliseconds}",
            Toast.LENGTH_LONG
        ).show()

    }
}
