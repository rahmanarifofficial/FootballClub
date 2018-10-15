package com.rahmanarif.footballclub.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rahmanarif.footballclub.R
import com.rahmanarif.footballclub.model.Events
import kotlinx.android.synthetic.main.item_list.view.*
import java.text.SimpleDateFormat
import java.util.*

class EventListAdapter(private var events: List<Events>, private val listener: (Events) -> Unit)
    : RecyclerView.Adapter<EventListAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        return holder.bindEvent(events[position], listener)
    }


    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindEvent(events: Events, listener: (Events) -> Unit) {
            val timeEvent = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .parse(events.dateEvent)
            val dateEvent = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
                    .format(timeEvent)

            itemView.date.text = dateEvent
            itemView.club_home.text = events.homeTeam
            itemView.score_home.text = events.homeScore
            itemView.score_away.text = events.awayScore
            itemView.club_away.text = events.awayTeam
            itemView.vs.text = "VS"
            itemView.setOnClickListener {
                listener(events)
            }
        }
    }
}