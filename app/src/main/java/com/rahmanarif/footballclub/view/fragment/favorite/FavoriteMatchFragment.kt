package com.rahmanarif.footballclub.view.fragment.favorite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rahmanarif.footballclub.R
import com.rahmanarif.footballclub.adapter.FavoriteEventListAdapter
import com.rahmanarif.footballclub.db.EventDB
import com.rahmanarif.footballclub.db.database
import com.rahmanarif.footballclub.view.activity.detail_event.DetailEventActivity
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class FavoriteMatchFragment : Fragment() {
    private var favorites: MutableList<EventDB> = mutableListOf()
    private lateinit var favoriteList: RecyclerView
    private lateinit var adapter: FavoriteEventListAdapter

    private fun showFavorite(){
        context?.database?.use {
            val result = select(EventDB.TABLE_EVENT)
            val favorite = result.parseList(classParser<EventDB>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_favorite_match, container, false)
        favoriteList = v.find(R.id.favorite_match) as RecyclerView

        favoriteList.layoutManager = LinearLayoutManager(activity)
        adapter = FavoriteEventListAdapter(favorites) {
            activity?.startActivity<DetailEventActivity>("idEvent" to "${it.eventId}")
        }
        favoriteList.adapter = adapter

        showFavorite()
        return v
    }

    companion object {
        fun matchIntance(): FavoriteMatchFragment = FavoriteMatchFragment()
    }
}
