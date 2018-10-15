package com.rahmanarif.footballclub.view.activity.detail_team

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.rahmanarif.footballclub.R
import com.rahmanarif.footballclub.adapter.PlayerListAdapter
import com.rahmanarif.footballclub.api.ApiRepository
import com.rahmanarif.footballclub.db.TeamDB
import com.rahmanarif.footballclub.db.database
import com.rahmanarif.footballclub.model.Player
import com.rahmanarif.footballclub.model.Team
import com.rahmanarif.footballclub.presenter.DetailTeamPresenter
import com.rahmanarif.footballclub.view.activity.detail_player.DetailPlayerActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class DetailTeamActivity : AppCompatActivity(), DetailViewTeam {
    private var idTeam: String = ""
    private var player: MutableList<Player> = mutableListOf()
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var team: Team
    private lateinit var presenter: DetailTeamPresenter
    private lateinit var adapter: PlayerListAdapter
    private lateinit var playerList: RecyclerView


    override fun getDetailTeam(data: Team) {
        team = Team(data.teamId, data.teamName, data.teamBadge)
        when (data.teamId) {
            idTeam -> {
                Picasso.get().load(data.teamBadge).into(logo_team)
                club_team.text = data.teamName
                club_year_formed_team.text = data.teamFormedYear
                club_stadium_team.text = data.teamStadium
                overview_team.text = data.teamDescription
                playerList = findViewById(R.id.player_team_list)

                playerList.layoutManager = LinearLayoutManager(this)
                adapter = PlayerListAdapter(player) {
                    startActivity<DetailPlayerActivity>("idPlayer" to "${it.playerId}")
                }
                playerList.adapter = adapter

                presenter.getPlayerTeam(idTeam)
            }
        }
    }

    override fun showPlayer(data: List<Player>) {
        player.clear()
        player.addAll(data)
        adapter.notifyDataSetChanged()
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
                if (isFavorite) {
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

    private fun addToFavorite() {
        val TAG: String = "TAG1"
        try {
            database.use {
                insert(TeamDB.TABLE_TEAMS,
                        TeamDB.TEAMS_ID to team.teamId,
                        TeamDB.TEAMS_NAME to team.teamName,
                        TeamDB.BADGE_TEAM to team.teamBadge
                )
            }
            toast("Ditambahkan ke favorite")

            Log.d(TAG, team.teamName)
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(TeamDB.TABLE_TEAMS, "(TEAMS_ID = {teamId})", "teamId" to idTeam)
            }
            toast("Dihapus dari favorite")
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_filled_24dp)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_24dp)
    }

    private fun favoriteState() {
        database.use {
            val result = select(TeamDB.TABLE_TEAMS).whereArgs("(TEAMS_ID = {id})", "id" to idTeam)
            val favorite = result.parseList(classParser<TeamDB>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        supportActionBar?.setTitle("Team Detail")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        idTeam = intent.getStringExtra("idTeam")

        //bind view pager

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailTeamPresenter(this, request, gson)

        presenter.getDetailTeam(idTeam)
    }

//    private fun setupViewPager(pager: ViewPager) {
//        val adapter = supportFragmentManager?.let { TabPagerAdapter(it) }
//
//        val next = OverviewFragment.newInstance()
//        adapter?.addFragment(next, "OVERVIEW")
//
//        val past = PlayerFragment.newInstance()
//        adapter?.addFragment(past, "PLAYER")
//
//        pager?.adapter = adapter
//    }
}
