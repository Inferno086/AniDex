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
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.inferno.anidex.databinding.FragmentAltCharacterBinding
import java.io.ByteArrayOutputStream

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

                when(menuItem.itemId) {
                    R.id.share -> {
                        val sendIntent: Intent = Intent().apply {
                            val charDes: String = binding.characterName.text.toString() + "\n\n" +
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
                            putExtra(Intent.EXTRA_TEXT, charDes)
                        }

                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(shareIntent)
                    }

                    android.R.id.home -> {
                        requireActivity().onBackPressed()
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}