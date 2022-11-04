package com.inferno.anidex

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.inferno.anidex.databinding.FragmentAltCharacterBinding

class AltCharacterFragment : Fragment(R.layout.fragment_alt_character) {

    lateinit var binding : FragmentAltCharacterBinding
    private val args by navArgs<AltCharacterFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAltCharacterBinding.bind(view)

        // Getting the arguments (Fragment Arguments)
        binding.characterName.text = args.charName
        binding.characterDescription.text = args.charDescription
        binding.characterImage.setImageResource(args.charImageID)
    }
}