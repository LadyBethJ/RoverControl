package com.mjb.rovercontrol.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.mjb.rovercontrol.R
import com.mjb.rovercontrol.databinding.FragmentStartBinding

/**
 * A simple [Fragment] subclass.
 * Use the [StartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private lateinit var navOptions: NavOptions

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
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)

        binding.startBtn.setOnClickListener { view : View ->
            view.findNavController().navigate(
                R.id.action_startFragment_to_inputFragment,
                null,
                navOptions
            )
        }

        return binding.root
    }

}