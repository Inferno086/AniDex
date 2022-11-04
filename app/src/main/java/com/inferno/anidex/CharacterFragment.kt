package com.inferno.anidex

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.inferno.anidex.databinding.FragmentCharacterBinding

class CharacterFragment : Fragment(R.layout.fragment_character) {

    lateinit var binding : FragmentCharacterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCharacterBinding.bind(view)

        // List of Characters
        val characters = listOf<Character>(
            Character("Eikichi Onizuka", getString(R.string.onizuka_description), R.drawable.onizuka),
            Character("Naruto Uzumaki", getString(R.string.naruto_description), R.drawable.naruto),
            Character(name = "Shinichi Izumi", getString(R.string.shinichi_discription), R.drawable.shinichi)
        )
        updateCharacter(characters[0])
    }

    private fun updateCharacter(character: Character) {
        binding.characterName.text = character.name
        binding.characterDescription.text = character.description
        binding.characterImage.setImageResource(character.image)
    }
}