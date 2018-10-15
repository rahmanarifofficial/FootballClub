package com.rahmanarif.footballclub.presenter

import com.google.gson.Gson
import com.rahmanarif.footballclub.api.ApiRepository
import com.rahmanarif.footballclub.api.TheSportDBApi
import com.rahmanarif.footballclub.model.EventsResponse
import com.rahmanarif.footballclub.util.CoroutineContextProvider
import com.rahmanarif.footballclub.view.fragment.schedule.MatchView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchPresenter(private val view: MatchView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPastEventsList(idLeague: String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPastEvents(idLeague)),
                        EventsResponse::class.java
                )
            }
            view.showPastEvents(data.await().events)
            view.hideLoading()
        }
    }

    fun getNextEventsList(idLeague: String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNextEvents(idLeague)),
                        EventsResponse::class.java
                )
            }
            view.showNextEvents(data.await().events)
            view.hideLoading()
        }
    }

    fun getSearchEventList(match: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getSearchEvent(match)),
                        EventsResponse::class.java
                )
            }
            view.showNextEvents(data.await().searchEvent)
            view.hideLoading()
        }
    }
}
