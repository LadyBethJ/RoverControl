package com.mjb.rovercontrol.data.model

import com.google.gson.annotations.SerializedName

data class Input(
    @SerializedName("topRightCorner")
    val topRightCorner: Coordinate?,
    @SerializedName("roverPosition")
    val roverPosition: Coordinate?,
    @SerializedName("roverDirection")
    val roverDirection: String?,
    @SerializedName("movements")
    val movements: String?
)

data class Coordinate(
    @SerializedName("x")
    val x: Int?,
    @SerializedName("y")
    val y: Int?
)