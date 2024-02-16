package com.example.flixter

import com.google.gson.annotations.SerializedName

class Movie {
    @SerializedName("title")
    var title: String? = null

    @SerializedName("overview")
    var overview: String? = null

    @SerializedName("poster_path")
    var posterpath: String? = null


}//end of class