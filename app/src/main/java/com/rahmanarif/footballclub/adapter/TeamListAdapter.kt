package com.rahmanarif.footballclub.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rahmanarif.footballclub.R
import com.rahmanarif.footballclub.model.Team
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_team_list.view.*

class TeamListAdapter (private var items: List<Team>, private val listener: (Team) -> Unit) :
        RecyclerView.Adapter<TeamListAdapter.TeamViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TeamViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_team_list,parent,false))

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(items[position],listener)
    }

    override fun getItemCount(): Int = items.size

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bindItem (items: Team, listener: (Team) -> Unit){
            itemView.name_club.text = items.teamName
            Picasso.get().load(items.teamBadge).into(itemView.image_club)
            itemView.setOnClickListener{
                listener(items)
            }
        }
    }
}