package com.rahmanarif.footballclub.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rahmanarif.footballclub.R
import com.rahmanarif.footballclub.db.EventDB
import kotlinx.android.synthetic.main.item_list.view.*

class FavoriteEventListAdapter(private val favorite:List<EventDB>, private val listener: (EventDB)-> Unit):
        RecyclerView.Adapter<FavoriteEventListAdapter.FavoriteViewHolder>() {
    class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindItem(favorite: EventDB, listener: (EventDB) -> Unit){
            itemView.date.text = favorite.dateEvent
            itemView.club_home.text = favorite.homeTeam
            itemView.score_home.text = favorite.homeScore
            itemView.score_away.text = favorite.awayScore
            itemView.club_away.text = favorite.awayTeam
            itemView.vs.text = "VS"
            itemView.setOnClickListener{
                listener(favorite)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }
}