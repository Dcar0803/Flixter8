package com.example.flixter


import com.google.gson.annotations.SerializedName

data class PersonDetails(
    @SerializedName("name") val name: String?,
    @SerializedName("known_for_department") val knownFor: String?, // Change the key to match the JSON response
    @SerializedName("biography") val description: String?,
    @SerializedName("profile_path") val profilePath: String?
)

