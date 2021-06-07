package com.mjb.rovercontrol.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mjb.rovercontrol.R
import com.mjb.rovercontrol.data.model.Input
import com.mjb.rovercontrol.databinding.FragmentInputBinding
import com.mjb.rovercontrol.domain.getJsonDataFromAsset

/**
 * A simple [Fragment] subclass.
 * Use the [InputFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InputFragment : Fragment() {

    private lateinit var binding: FragmentInputBinding
    private lateinit var navOptions: NavOptions
    private lateinit var jsonFileString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navOptions = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        jsonFileString = context?.let { getJsonDataFromAsset(it, "data.json") } ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInputBinding.inflate(inflater, container, false)

        binding.inputTv.text = jsonFileString
        val action = InputFragmentDirections.actionInputFragmentToResultsFragment(jsonFileString)

        binding.resultsBtn.setOnClickListener { view : View ->
            view.findNavController().navigate(action,navOptions)
        }

        return binding.root
    }

}