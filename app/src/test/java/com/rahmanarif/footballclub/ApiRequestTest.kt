package com.rahmanarif.footballclub

import com.rahmanarif.footballclub.api.ApiRepository
import com.rahmanarif.footballclub.api.TheSportDBApi
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRequestTest {
    @Test
    fun testSearchEventByName() {
        val apiRepository = mock(ApiRepository::class.java)
        val url = TheSportDBApi.getSearchEvent("Chelsea")
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }

    @Test
    fun testSearchTeam() {
        val apiRepository = mock(ApiRepository::class.java)
        val url = TheSportDBApi.getSearchTeam("Liverpool")
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}