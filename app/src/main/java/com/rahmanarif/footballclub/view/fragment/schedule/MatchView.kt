package com.rahmanarif.footballclub.view.fragment.schedule

import com.rahmanarif.footballclub.model.Events

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showPastEvents(data: List<Events>)
    fun showNextEvents(data: List<Events>)
}