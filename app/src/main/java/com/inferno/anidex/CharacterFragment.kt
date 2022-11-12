package com.inferno.anidex

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.inferno.anidex.databinding.FragmentCharacterBinding
import java.io.ByteArrayOutputStream
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
        binding.characterImage.setOnClickListener {
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

    // This function is for inflating the share button and back button
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

                when(menuItem.itemId) {
                    // For sharing the Text in description and the Image
                    R.id.share -> {
                        // sendIntent is what we want to share
                        val sendIntent : Intent = Intent().apply {
                            val charDescrip: String =
                                binding.characterName.text.toString() + "\n\n" +
                                        binding.characterDescription.text.toString()

                            // For sharing the image, we prepare it by this code
                            // https://sagar-r-kothari.github.io/android/kotlin/2020/07/20/Android-Share-Intent-Image.html

                            // Get Bitmap from your imageView
                            val bitmap = binding.characterImage.drawable.toBitmap()
                            // Compress image
                            val bytes = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                            // Save image & get path of it
                            // insertImage() is deprecated but it still works here
                            val path = MediaStore.Images.Media.insertImage(requireContext().contentResolver, bitmap, "tempimage", null)
                            // Get URI of saved image
                            val uri = Uri.parse(path)


                            type = "image/*"
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_STREAM, uri)
                            putExtra(Intent.EXTRA_TEXT, charDescrip)
                        }

                        // shareIntent chooses the correct activity based on sendIntent
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(shareIntent)
                    }

                    android.R.id.home -> {
                        // I used requireActivity()
                        // Because onBackPressed() does not work in fragments directly
                        requireActivity().onBackPressed()
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED )
    }
}