package com.rahmanarif.footballclub.view.fragment.team

import com.rahmanarif.footballclub.model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}