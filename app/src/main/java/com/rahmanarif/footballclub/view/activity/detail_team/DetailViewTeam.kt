package com.rahmanarif.footballclub.view.activity.detail_team

import com.rahmanarif.footballclub.model.Player
import com.rahmanarif.footballclub.model.Team

interface DetailViewTeam {
    fun getDetailTeam(data: Team)
    fun showPlayer(data: List<Player>)
}