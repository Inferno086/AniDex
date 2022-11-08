package com.inferno.anidex

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.inferno.anidex.databinding.FragmentAltCharacterBinding

class AltCharacterFragment : Fragment(R.layout.fragment_alt_character) {

    lateinit var binding : FragmentAltCharacterBinding
    private val args by navArgs<AltCharacterFragmentArgs>()

    // I added onCreate() as well just for the animations
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // For enter animations
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAltCharacterBinding.bind(view)

        // Getting the arguments (Fragment Arguments)
        binding.characterName.text = args.charName
        binding.characterDescription.text = args.charDescription
        binding.characterImage.setImageResource(args.charImageID)

        // Adding the share button
        val menuHost : MenuHost = requireActivity()
        shareButtonInflater(menuHost)
    }

    private fun shareButtonInflater(menuHost : MenuHost) {
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.share_button, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                val sendIntent : Intent = Intent().apply {
                    val charDes : String = binding.characterName.text.toString() + "\n\n" +
                            binding.characterDescription.text.toString()
                    type = "text/plain"
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, charDes)
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}