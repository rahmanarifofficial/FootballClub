package com.rahmanarif.footballclub.api

object TheSportDBApi {
    fun getPastEvents(idLeague: String?): String {
        return "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id="+idLeague
    }

    fun getNextEvents(idLeague: String?): String {
        return "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id="+idLeague
    }

    fun getDetailEvent(idEvent: String?): String{
        return "https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id="+idEvent
    }

    fun getTeamList(league: String?): String{
        return "https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l="+league
    }

    fun getTeamDetail(idTeam: String?): String{
        return "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id="+idTeam
    }

    fun getPlayerTeam(idTeam: String?): String{
        return "https://www.thesportsdb.com/api/v1/json/1/lookup_all_players.php?id="+idTeam
    }

    fun getPlayerDetail(idPlayer: String): String{
        return "https://www.thesportsdb.com/api/v1/json/1/lookupplayer.php?id="+idPlayer
    }

    fun getLeague(): String {
        return "https://www.thesportsdb.com/api/v1/json/1/all_leagues.php"
    }
}