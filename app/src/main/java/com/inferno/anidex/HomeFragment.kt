package com.inferno.anidex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.inferno.anidex.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        // onClickListener for startButton
        binding.startButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_homeFragment_to_characterFragment)
        }

        // onClickListener for listButton
        binding.listButton.setOnClickListener { view ->
            view.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToListFragment())
        }

        return binding.root
    }
}