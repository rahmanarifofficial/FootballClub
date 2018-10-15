package com.rahmanarif.footballclub.model

import com.google.gson.annotations.SerializedName

data class League(
        @SerializedName("idLeague")
        var idLeague: String? = null,

        @SerializedName("strLeague")
        var league: String? = null
)