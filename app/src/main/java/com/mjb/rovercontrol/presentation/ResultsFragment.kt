package com.mjb.rovercontrol.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mjb.rovercontrol.R
import com.mjb.rovercontrol.data.model.Coordinate
import com.mjb.rovercontrol.data.model.Input
import com.mjb.rovercontrol.databinding.FragmentResultsBinding
import com.mjb.rovercontrol.domain.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [ResultsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultsFragment : Fragment() {

    private lateinit var binding: FragmentResultsBinding
    private lateinit var navOptions: NavOptions
    private val args: ResultsFragmentArgs by navArgs()
    private lateinit var roverInstructions: Input

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
        binding = FragmentResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        generateRoverInstructions()
    }

    private fun generateRoverInstructions() {
        val roverInstructionsInput = object : TypeToken<Input>() {}.type

        var roverInput: Input = Gson().fromJson(args.roverInstructions, roverInstructionsInput)
        roverInstructions = roverInput
        checkRoverInput()
    }

    private fun checkRoverInput() {
        checkTopRightCorner()
    }

    private fun checkTopRightCorner() {
        val topRightCorner = roverInstructions.topRightCorner
        if (topRightCorner != null) {
            if (topRightCorner.x != null){
                if (topRightCorner.y != null){
                    checkRoverPosition()
                } else {
                    binding.outputTv.text = requireContext().getString(R.string.error_topRightCorner_coordinates)
                }
            } else {
                binding.outputTv.text = requireContext().getString(R.string.error_topRightCorner_coordinates)
            }
        } else {
            binding.outputTv.text = requireContext().getString(R.string.error_topRightCorner_empty)
        }
    }

    private fun checkRoverPosition() {
        val roverPosition = roverInstructions.roverPosition
        if (roverPosition != null) {
            if (roverPosition.x != null){
                if (roverPosition.y != null){
                    checkRoverDirection()
                } else {
                    binding.outputTv.text = requireContext().getString(R.string.error_roverPosition_coordinates)
                }
            } else {
                binding.outputTv.text = requireContext().getString(R.string.error_roverPosition_coordinates)
            }
        } else {
            binding.outputTv.text = requireContext().getString(R.string.error_roverPosition_empty)
        }
    }

    private fun checkMovements() {
        val roverMovementsUpper = roverInstructions.movements.toString()
            .uppercase(Locale.getDefault())

        if(roverMovementsUpper.isNullOrEmpty()){
            binding.outputTv.text = requireContext().getString(R.string.error_movements_empty)
        } else {
            if(!(roverMovementsUpper.contains("M")
                || roverMovementsUpper.contains("L")
                || roverMovementsUpper.contains("R"))
            ) {
                binding.outputTv.text = requireContext().getString(R.string.error_movements_format)
            } else {
                calculateOutput(roverMovementsUpper)
            }
        }
    }

    private fun calculateOutput(movements: String) {
        val realMovements = prepareMovement(movements)
        val topRightCornerX = roverInstructions.topRightCorner?.x
        val topRightCornerY = roverInstructions.topRightCorner?.y
        val roverPositionX = roverInstructions.roverPosition?.x
        val roverPositionY = roverInstructions.roverPosition?.y
        val roverDirection = roverInstructions.roverDirection.toString()
            .uppercase(Locale.getDefault())

        var auxPosition = Coordinate(roverPositionX,roverPositionY)
        var auxDirection = roverDirection

        if((roverPositionX!! > topRightCornerX!!) || (roverPositionY!! > topRightCornerY!!)) {
            binding.outputTv.text = requireContext()
                .getString(R.string.error_topRightCorner_roverPosition)
        } else {
            for (i in realMovements) {
                when(i) {
                    'M' -> auxPosition = updateRoverPosition(auxPosition, auxDirection)
                    'L' -> auxDirection = turnLeftRoverDirection(auxDirection)
                    'R' -> auxDirection = turnRightRoverDirection(auxDirection)
                }
            }
        }

        binding.outputTv.text = "${auxPosition.x},${auxPosition.y}, $auxDirection"
    }

    private fun updateRoverPosition(position: Coordinate, direction: String): Coordinate {
        var positionX = position.x
        var positionY = position.y
        val topRightCorner = roverInstructions.topRightCorner

        when (direction) {
            "N" -> positionY = increasePosition(positionY!!, topRightCorner?.y!!)
            "S" -> positionY = decreasePosition(positionY!!, 0)
            "W" -> positionX = decreasePosition(positionX!!, 0)
            else -> positionX = increasePosition(positionX!!, topRightCorner?.x!!)
        }

        return Coordinate(positionX, positionY)
    }

    private fun prepareMovement(movements: String): String {
        var result = ""
        for (i in movements) {
            when(i) {
                'M' -> result += i
                'L' -> result += i
                'R' -> result += i
            }
        }
        return result
    }

    private fun checkRoverDirection() {
        val roverDirectionUpper = roverInstructions.roverDirection.toString()
            .uppercase(Locale.getDefault())

        if(roverDirectionUpper.isNullOrEmpty() || roverDirectionUpper.equals("NULL")){
            binding.outputTv.text = requireContext().getString(R.string.error_roverDirection_empty)
        } else {
            if(!checkCardinalPoint(roverDirectionUpper)) {
                binding.outputTv.text = requireContext().getString(R.string.error_roverDirection_format)
            } else {
                checkMovements()
            }
        }
    }
}