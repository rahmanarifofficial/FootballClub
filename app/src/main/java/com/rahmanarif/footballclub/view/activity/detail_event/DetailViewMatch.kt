package com.rahmanarif.footballclub.view.activity.detail_event

import com.rahmanarif.footballclub.model.Team
import com.rahmanarif.footballclubschedule.model.DetailEvent

interface DetailViewMatch {
    fun showTeam(data: Team)
    fun getDetailEvent(data: DetailEvent)
}