package com.rahmanarif.footballclub.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rahmanarif.footballclub.R
import com.rahmanarif.footballclub.db.TeamDB
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_team_list.view.*

class FavoriteTeamListAdapter(private val favorite:List<TeamDB>, private val listener: (TeamDB)-> Unit):
        RecyclerView.Adapter<FavoriteTeamListAdapter.FavoriteViewHolder>() {
    class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindItem(favorite: TeamDB, listener: (TeamDB) -> Unit){
            itemView.name_club.text = favorite.teamName
            Picasso.get().load(favorite.badgeTeam).into(itemView.image_club)
            itemView.setOnClickListener{
                listener(favorite)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_team_list, parent, false))
    }

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }
}