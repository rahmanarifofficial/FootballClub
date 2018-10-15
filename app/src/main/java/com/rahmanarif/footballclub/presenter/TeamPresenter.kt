package com.rahmanarif.footballclub.presenter

import com.google.gson.Gson
import com.rahmanarif.footballclub.api.ApiRepository
import com.rahmanarif.footballclub.api.TheSportDBApi
import com.rahmanarif.footballclub.model.TeamResponse
import com.rahmanarif.footballclub.util.CoroutineContextProvider
import com.rahmanarif.footballclub.view.fragment.team.TeamView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamPresenter(private val view: TeamView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getTeamList(league)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamList(data.teams)
            }
        }
    }

    fun getSearchTeam(team: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getSearchTeam(team)),
                        TeamResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }
}