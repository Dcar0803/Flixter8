package com.example.flixter

import com.google.gson.annotations.SerializedName

data class PersonResponse(
    @SerializedName("results")
    val persons: List<Person>
)
