package com.rahmanarif.footballclub.presenter

import com.google.gson.Gson
import com.rahmanarif.footballclub.api.ApiRepository
import com.rahmanarif.footballclub.api.TheSportDBApi
import com.rahmanarif.footballclub.model.PlayerResponse
import com.rahmanarif.footballclub.model.TeamResponse
import com.rahmanarif.footballclub.util.CoroutineContextProvider
import com.rahmanarif.footballclub.view.activity.detail_team.DetailViewTeam
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailTeamPresenter(private val view: DetailViewTeam,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getDetailTeam(idTeam: String) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetail(idTeam)),
                        TeamResponse::class.java)
            }
            view.getDetailTeam(data.await().teams.first())
        }
    }

    fun getPlayerTeam(idTeam: String){
        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayerTeam(idTeam)),
                        PlayerResponse::class.java)
            }
            view.showPlayer(data.await().player)
        }
    }
}