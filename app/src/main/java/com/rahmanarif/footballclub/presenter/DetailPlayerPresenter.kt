package com.rahmanarif.footballclub.presenter

import com.google.gson.Gson
import com.rahmanarif.footballclub.api.ApiRepository
import com.rahmanarif.footballclub.api.TheSportDBApi
import com.rahmanarif.footballclub.model.PlayerResponse
import com.rahmanarif.footballclub.util.CoroutineContextProvider
import com.rahmanarif.footballclub.view.activity.detail_player.DetailViewPlayer
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailPlayerPresenter(private val view: DetailViewPlayer,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getDetailPlayer(idPlayer: String) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.
                        doRequest(TheSportDBApi.getPlayerDetail(idPlayer)),
                        PlayerResponse::class.java)
            }
            view.getDetailPlayer(data.await().players.first())
        }
    }
}