package com.rahmanarif.footballclub

import com.google.gson.Gson
import com.rahmanarif.footballclub.api.ApiRepository
import com.rahmanarif.footballclub.api.TheSportDBApi
import com.rahmanarif.footballclub.model.Player
import com.rahmanarif.footballclub.model.PlayerResponse
import com.rahmanarif.footballclub.presenter.DetailPlayerPresenter
import com.rahmanarif.footballclub.view.activity.detail_player.DetailViewPlayer
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailPlayerTest {
    @Mock
    private lateinit var view: DetailViewPlayer

    @Mock
    private lateinit var gson: Gson

    @Mock
    lateinit var apiRequest: ApiRepository

    private lateinit var presenter: DetailPlayerPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPlayerPresenter(view, apiRequest, gson, TestContextProvider())
    }

    @Test
    fun getTestDetailPlayers() {
        val idPlayer = "34145411"

        val detailPlayer = listOf<Player>(Player(idPlayer))

        val playerResponse = PlayerResponse(detailPlayer, detailPlayer)

        Mockito.`when`(gson.fromJson(apiRequest.doRequest(TheSportDBApi.getPlayerDetail(idPlayer)),
                PlayerResponse::class.java)).thenReturn(playerResponse)

        presenter.getDetailPlayer(idPlayer)

        Mockito.verify(view).getDetailPlayer(detailPlayer.first())
    }
}