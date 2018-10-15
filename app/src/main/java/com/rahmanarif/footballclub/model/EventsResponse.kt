package com.rahmanarif.footballclub.model

import com.google.gson.annotations.SerializedName

data class EventsResponse(
        val events: List<Events>,
        @SerializedName("event")
        val searchEvent: List<Events>
)