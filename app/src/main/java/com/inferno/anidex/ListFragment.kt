package com.inferno.anidex

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.inferno.anidex.databinding.FragmentListBinding

class ListFragment : Fragment() {

    lateinit var binding : FragmentListBinding

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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list,container,false)

        // onClickListener for all TextViews
        setListenersForTextViews()

        return binding.root
    }

    private fun setListenersForTextViews() {
        lateinit var characterTextViews : List<TextView>

        binding.apply {
            characterTextViews = listOf(
                narutoTextView,
                shinichiTextView,
                onizukaTextView,
                josephjoestarTextView,
                gutsTextView,
            )
        }

        characterTextViews.forEach {
            it.setOnClickListener {
                navigateToAltCharacterFragment(it)
            }
        }
    }

    private fun navigateToAltCharacterFragment(view: View?) {
        when((view as TextView).text) {
            "Eikichi Onizuka" -> setCharPropertiesToNavigate(view, R.string.onizuka, R.string.onizuka_description, R.drawable.onizuka)
            "Naruto Uzumaki" -> setCharPropertiesToNavigate(view, R.string.naruto, R.string.naruto_description, R.drawable.naruto)
            "Shinichi Izumi" -> setCharPropertiesToNavigate(view, R.string.shinichi, R.string.shinichi_description, R.drawable.shinichi)
            "Joseph Joestar" -> setCharPropertiesToNavigate(view, R.string.joseph_joestar, R.string.josephjoestar_description, R.drawable.josephjoestar)
            "Guts" -> setCharPropertiesToNavigate(view, R.string.guts, R.string.guts_description, R.drawable.guts)
        }
    }

    private fun setCharPropertiesToNavigate(view : TextView, name : Int, descriptionID : Int, imageID : Int) {
        view.findNavController().navigate(ListFragmentDirections.actionListFragmentToAltCharacterFragment(charName = getString(name), charDescription = getString(descriptionID), charImageID = imageID))
    }
}