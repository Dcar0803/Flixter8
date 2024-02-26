package com.example.flixter

import com.google.gson.annotations.SerializedName

class Person {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("biography")
    var biography: String? = null

    @SerializedName("profile_path")
    var profilePath: String? = null
}
