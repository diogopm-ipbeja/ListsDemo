package pt.ipbeja.listsdemo.ui.scrollview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import pt.ipbeja.listsdemo.R
import pt.ipbeja.listsdemo.databinding.FragmentScrollingBinding
import pt.ipbeja.listsdemo.ui.utils.content
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@ExperimentalTime
class ScrollingFragment : Fragment() {

    private lateinit var binding: FragmentScrollingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = with(FragmentScrollingBinding.inflate(inflater)) {
        binding = this
        root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.viewsCountBtn.setOnClickListener {
            val input = binding.viewsCountInput.content
            val count = if(input.isBlank()) 0 else input.toInt()

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
