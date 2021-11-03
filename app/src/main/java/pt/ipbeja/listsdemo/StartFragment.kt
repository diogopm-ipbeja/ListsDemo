package pt.ipbeja.listsdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import pt.ipbeja.listsdemo.databinding.FragmentStartBinding

class StartFragment : Fragment() {


    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = with(FragmentStartBinding.inflate(inflater)) {
        binding = this
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.scrollviewBtn.setOnClickListener(Navigation.createNavigateOnClickListener(StartFragmentDirections.actionStartFragmentToScrollingFragment()))
        binding.recyclerviewBtn.setOnClickListener(Navigation.createNavigateOnClickListener(StartFragmentDirections.actionStartFragmentToRecyclerViewFragment()))
    }
}