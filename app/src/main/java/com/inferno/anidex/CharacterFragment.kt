package com.inferno.anidex

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.inferno.anidex.databinding.FragmentCharacterBinding
import kotlin.random.Random

class CharacterFragment : Fragment(R.layout.fragment_character) {

    lateinit var binding : FragmentCharacterBinding
    val totalCharacters : Int = 5

    // I added onCreate() as well just for the animations
    // I dont know if it can be done in onViewCreated()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // For enter animations
        val inflater = androidx.transition.TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCharacterBinding.bind(view)  //Alternate way to bind a fragment

        // List of Characters
        val characters = listOf<Character>(
            Character("Eikichi Onizuka", getString(R.string.onizuka_description), R.drawable.onizuka),
            Character("Guts", getString(R.string.guts_description), R.drawable.guts),
            Character("Joseph Joestar", getString(R.string.josephjoestar_description), R.drawable.josephjoestar),
            Character("Naruto Uzumaki", getString(R.string.naruto_description), R.drawable.naruto),
            Character(name = "Shinichi Izumi", getString(R.string.shinichi_description), R.drawable.shinichi),
        )

        displayRandomCharacterFromList(characters, totalCharacters) // For the first time

        //onClickListener for the image
        binding.characterImage.setOnClickListener { view ->
            displayRandomCharacterFromList(characters, totalCharacters)
        }

        // For share button
        val menuHost : MenuHost = requireActivity()
        shareButtonInflater(menuHost)
    }

    // This function updates the character displayed when called
    private fun updateCharacter(character: Character) {
        binding.characterName.text = character.name
        binding.characterDescription.text = character.description
        binding.characterImage.setImageResource(character.image)
    }

    // This function uses updateCharacter() to display a random character
    // This is called when the user clicks the ImageView
    private fun displayRandomCharacterFromList(list : List<Character>, totalNoOfCharacters : Int) {
        val randomNumber : Int = Random.nextInt(0,totalNoOfCharacters)
        updateCharacter(list[randomNumber])
    }

    // This function is for inflating the share button
    // This can be done directly in onViewCreated() as well
    // It has been done for simplicity in onViewCreated()
    // setHasOptionsMenu(true) has been deprecated and this is the replacement
    private fun shareButtonInflater(menuHost : MenuHost) {
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.share_button, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // For sharing the Text in description
                when(menuItem.itemId) {
                    R.id.share -> {
                            val sendIntent : Intent = Intent().apply {
                                val charDescrip: String =
                                    binding.characterName.text.toString() + "\n\n" +
                                            binding.characterDescription.text.toString()
                                type = "text/plain"
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, charDescrip)
                            }

                            val shareIntent = Intent.createChooser(sendIntent, null)
                            startActivity(shareIntent)
                        }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED )
    }
}