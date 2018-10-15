package com.rahmanarif.footballclub.view.fragment.favorite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rahmanarif.footballclub.R
import com.rahmanarif.footballclub.adapter.FavoriteTeamListAdapter
import com.rahmanarif.footballclub.db.TeamDB
import com.rahmanarif.footballclub.db.database
import com.rahmanarif.footballclub.view.activity.detail_team.DetailTeamActivity
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class FavoriteTeamFragment : Fragment() {
    private var favorites: MutableList<TeamDB> = mutableListOf()
    private lateinit var favoriteList: RecyclerView
    private lateinit var adapter: FavoriteTeamListAdapter

    private fun showFavorite(){
        context?.database?.use {
            val result = select(TeamDB.TABLE_TEAMS)
            val favorite = result.parseList(classParser<TeamDB>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_favorite_team, container, false)
        favoriteList = v.find(R.id.favorite_team) as RecyclerView

        favoriteList.layoutManager = LinearLayoutManager(activity)
        adapter = FavoriteTeamListAdapter(favorites) {
            activity?.startActivity<DetailTeamActivity>("idTeam" to "${it.teamId}")
        }
        favoriteList.adapter = adapter

        showFavorite()
        return v
    }

    companion object {
        fun teamIntance(): FavoriteTeamFragment = FavoriteTeamFragment()
    }}
