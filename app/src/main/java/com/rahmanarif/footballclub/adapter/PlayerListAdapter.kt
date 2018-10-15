package com.rahmanarif.footballclub.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rahmanarif.footballclub.R
import com.rahmanarif.footballclub.model.Player
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_player_list.view.*

class PlayerListAdapter (private var items: List<Player>, private val listener: (Player) -> Unit):
        RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder =
            PlayerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_player_list,parent,false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(items[position],listener)
    }

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem (items: Player, listener: (Player) -> Unit){
            Picasso.get().load(items.strCutout).into(itemView.image_player_team)
            itemView.name_player_team.text = items.strPlayer
            itemView.position_player_team.text = items.strPosition
            itemView.setOnClickListener{
                listener(items)
            }
        }
    }
}