package com.rahmanarif.footballclub

import com.google.gson.Gson
import com.rahmanarif.footballclub.api.ApiRepository
import com.rahmanarif.footballclub.api.TheSportDBApi
import com.rahmanarif.footballclub.model.Events
import com.rahmanarif.footballclub.model.EventsResponse
import com.rahmanarif.footballclub.presenter.MatchPresenter
import com.rahmanarif.footballclub.view.fragment.schedule.MatchView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EventsPresenterTest {
    @Mock
    private
    lateinit var view: MatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchPresenter


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testPastEventsList() {

        val event = listOf<Events>(Events("576548"))
        val response = EventsResponse(event, event)

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPastEvents("576548")),
                EventsResponse::class.java
        )).thenReturn(response)

        presenter.getPastEventsList("576548")

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showPastEvents(event)
        Mockito.verify(view).hideLoading()

    }
}