package com.inferno.anidex

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.inferno.anidex.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding

    // I added onCreate() as well just for the animations
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // For enter animations
        val inflater = androidx.transition.TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
    }

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

        // Adding the options menu in HomeFragment
        val menuHost : MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_options_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    // If share is selected
                    R.id.home_options_share -> {
                        val sendIntent : Intent = Intent().apply {
                            type = "text/plain"
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "This app is made by Arjun Shukla" +
                                    " (Inferno)")
                        }

                        val shareIntent : Intent = Intent.createChooser(sendIntent, null)
                        startActivity(shareIntent)
                    }

                    // If list is selected
                    R.id.home_options_list -> {
                        Navigation.findNavController(view!!).navigate(HomeFragmentDirections.actionHomeFragmentToListFragment())
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }
}