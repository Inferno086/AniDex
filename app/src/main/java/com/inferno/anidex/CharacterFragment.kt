package com.inferno.anidex

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.inferno.anidex.databinding.FragmentCharacterBinding
import kotlin.random.Random

class CharacterFragment : Fragment(R.layout.fragment_character) {

    lateinit var binding : FragmentCharacterBinding
    val totalCharacters : Int = 3

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCharacterBinding.bind(view)  //Alternate way to bind a fragment

        // List of Characters
        val characters = listOf<Character>(
            Character("Eikichi Onizuka", getString(R.string.onizuka_description), R.drawable.onizuka),
            Character("Naruto Uzumaki", getString(R.string.naruto_description), R.drawable.naruto),
            Character(name = "Shinichi Izumi", getString(R.string.shinichi_discription), R.drawable.shinichi)
        )

        displayRandomCharacterFromList(characters, totalCharacters) // For the first time

        //onClickListener for the image
        binding.characterImage.setOnClickListener { view ->
            displayRandomCharacterFromList(characters, totalCharacters)
        }
    }

    private fun updateCharacter(character: Character) {
        binding.characterName.text = character.name
        binding.characterDescription.text = character.description
        binding.characterImage.setImageResource(character.image)
    }

    private fun displayRandomCharacterFromList(list : List<Character>, totalNoOfCharacters : Int) {
        val randomNumber : Int = Random.nextInt(0,totalNoOfCharacters)
        updateCharacter(list[randomNumber])
    }
}