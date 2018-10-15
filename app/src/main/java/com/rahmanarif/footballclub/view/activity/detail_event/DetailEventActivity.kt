package com.rahmanarif.footballclub.view.activity.detail_event

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.rahmanarif.footballclub.R
import com.rahmanarif.footballclub.api.ApiRepository
import com.rahmanarif.footballclub.db.EventDB
import com.rahmanarif.footballclub.db.database
import com.rahmanarif.footballclub.model.Team
import com.rahmanarif.footballclub.presenter.DetailEventPresenter
import com.rahmanarif.footballclubschedule.model.DetailEvent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_event.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class DetailEventActivity : AppCompatActivity(), DetailViewMatch {
    private var idEvent: String = ""
    private var idHomeTeam: String = ""
    private var idAwayTeam: String = ""
    private lateinit var presenter: DetailEventPresenter
    private lateinit var events: DetailEvent

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun showTeam(data: Team) {
        when(data.teamId) {
            idHomeTeam -> {
                Picasso.get().load(data.teamBadge).into(homeBadge)
            }
            idAwayTeam -> {
                Picasso.get().load(data.teamBadge).into(awayBadge)
            }
        }
    }

    override fun getDetailEvent(data: DetailEvent) {
        events = DetailEvent(data.eventId, data.event, data.league, data.homeTeam, data.awayTeam,
                data.homeScore, data.awayScore, data.homeTeamId, data.awayTeamId,data.homeGoalDetail, data.awayGoalDetail, data.homeRedCards,
                data.homeYellowCards, data.awayRedCards, data.awayYellowCards, data.dateEvent)
        idHomeTeam = data.homeTeamId.toString()
        idAwayTeam = data.awayTeamId.toString()
        Log.d("idHome", idHomeTeam)
        when(data.eventId){
            idEvent -> {
                eventName.text = data.event
                leagueName.text = data.league
                homeTeam.text = data.homeTeam
                awayTeam.text = data.awayTeam
                homeScore.text = data.homeScore
                awayScore.text = data.awayScore
                goalHomeDetail.text = data.homeGoalDetail
                goalAwayDetail.text = data.awayGoalDetail
                homeRedCardDetail.text = data.homeRedCards
                awayRedCardDetail.text = data.awayRedCards
                homeYellowCardDetail.text = data.homeYellowCards
                awayYellowCardDetail.text = data.awayYellowCards
                vs.text = "VS"
            }
        }
        presenter.getTeam(idHomeTeam)
        presenter.getTeam(idAwayTeam)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        favoriteState()
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite){
                    removeFromFavorite()
                } else {
                    addToFavorite()
                }

                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        val TAG: String = "TAG1"
        try {
            database.use {
                insert(EventDB.TABLE_EVENT,
                        EventDB.EVENT_ID to events.eventId,
                        EventDB.HOMETEAM_ID to events.homeTeamId,
                        EventDB.HOMETEAM to events.homeTeam,
                        EventDB.AWAYTEAM_ID to events.awayTeamId,
                        EventDB.AWAYTEAM to events.awayTeam,
                        EventDB.HOMESCORE to events.homeScore,
                        EventDB.AWAYSCORE to events.awayScore,
                        EventDB.DATEEVENT to events.dateEvent
                )
            }
            toast("Ditambahkan ke favorite")

            Log.d(TAG, events.homeTeam)
//            Log.d(TAG, Favorite.HOMETEAM)
//            Log.d(TAG, Favorite.AWAYTEAM)
//            Log.d(TAG, idHomeTeam)
//            Log.d(TAG, events.homeTeamId)
//            Log.d(TAG, "home team id")
        } catch(e: SQLiteConstraintException){
            toast(e.localizedMessage)
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(EventDB.TABLE_EVENT, "(EVENT_ID = {eventId})", "eventId" to idEvent)
            }
            toast("Dihapus dari favorite")
        } catch (e: SQLiteConstraintException){
            toast(e.localizedMessage)
        }
    }

    private fun setFavorite(){
        if(isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_filled_24dp)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_24dp)
    }

    private fun favoriteState(){
        database.use {
            val result = select(EventDB.TABLE_EVENT).whereArgs("(EVENT_ID = {id})", "id" to idEvent)
            val favorite = result.parseList(classParser<EventDB>())
            if (!favorite.isEmpty()) isFavorite = true
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        supportActionBar?.title = "Event Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        idEvent = intent.getStringExtra("idEvent")


        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailEventPresenter(this, request, gson)

        Log.d("idEvent", idEvent)
        presenter.getDetailEvent(idEvent)
    }
}
