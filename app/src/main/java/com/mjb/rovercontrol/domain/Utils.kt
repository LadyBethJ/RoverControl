package com.mjb.rovercontrol.domain

import android.content.Context
import java.io.IOException

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

fun checkCardinalPoint(direction: String): Boolean {
    return when (direction) {
        "N" -> true
        "S" -> true
        "W" -> true
        "E" -> true
        else -> false
    }
}

fun turnLeftRoverDirection(direction: String): String {
    return when (direction) {
        "N" -> "W"
        "S" -> "E"
        "W" -> "S"
        else -> "N"
    }
}

fun turnRightRoverDirection(direction: String): String {
    return when (direction) {
        "N" -> "E"
        "S" -> "W"
        "W" -> "N"
        else -> "S"
    }
}

fun increasePosition(origin: Int, limit: Int): Int {
    return if(origin + 1 <= limit){
        origin + 1
    } else origin
}

fun decreasePosition(origin: Int, limit: Int): Int {
    return if(origin - 1 >= limit){
        origin - 1
    } else origin
}