package pt.ipbeja.listsdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import pt.ipbeja.listsdemo.databinding.FragmentStartBinding

class StartFragment : Fragment() {


    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = with(FragmentStartBinding.inflate(inflater, container, false)) {
        _binding = this
        root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.scrollviewBtn.setOnClickListener(Navigation.createNavigateOnClickListener(StartFragmentDirections.actionStartFragmentToScrollingFragment()))
        binding.recyclerviewBtn.setOnClickListener(Navigation.createNavigateOnClickListener(StartFragmentDirections.actionStartFragmentToRecyclerViewFragment()))
    }
}