package com.rahmanarif.footballclub.presenter

import com.google.gson.Gson
import com.rahmanarif.footballclub.api.ApiRepository
import com.rahmanarif.footballclub.api.TheSportDBApi
import com.rahmanarif.footballclub.model.DetailEventResponse
import com.rahmanarif.footballclub.model.TeamResponse
import com.rahmanarif.footballclub.util.CoroutineContextProvider
import com.rahmanarif.footballclub.view.activity.detail_event.DetailViewMatch
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailEventPresenter(private val view: DetailViewMatch,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getTeam(idTeam: String) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamDetail(idTeam)),
                        TeamResponse::class.java)
            }
            view.showTeam(data.await().teams.first())
        }
    }

    fun getDetailEvent(idEvent: String) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getDetailEvent(idEvent)),
                        DetailEventResponse::class.java
                )
            }
            view.getDetailEvent(data.await().events.first())
        }
    }
}