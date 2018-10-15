package com.rahmanarif.footballclub.view.fragment.schedule

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import com.google.gson.Gson
import com.rahmanarif.footballclub.R
import com.rahmanarif.footballclub.adapter.EventListAdapter
import com.rahmanarif.footballclub.api.ApiRepository
import com.rahmanarif.footballclub.model.Events
import com.rahmanarif.footballclub.presenter.MatchPresenter
import com.rahmanarif.footballclub.util.invisible
import com.rahmanarif.footballclub.util.visible
import com.rahmanarif.footballclub.view.activity.detail_event.DetailEventActivity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh


class NextEventFragment : Fragment(), MatchView {
    private var events: MutableList<Events> = mutableListOf()
    private var leagueId: String = "4328"

    private lateinit var presenter: MatchPresenter
    private lateinit var eventList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var adapter: EventListAdapter
    private lateinit var leagueName: String


    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPastEvents(data: List<Events>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showNextEvents(data: List<Events>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    companion object {
        fun nextIntance(): NextEventFragment = NextEventFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        //spinner
        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchPresenter(this, request, gson)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                leagueName = spinner.selectedItem.toString()
                if (leagueName.equals("English Premier League", true)) {
                    leagueId = "4328"
                } else if (leagueName.equals("English League Championship", true)) {
                    leagueId = "4329"
                } else if (leagueName.equals("German Bundesliga", true)) {
                    leagueId = "4331"
                } else if (leagueName.equals("Italian Serie A", true)) {
                    leagueId = "4332"
                } else if (leagueName.equals("French Ligue 1", true)) {
                    leagueId = "4334"
                } else if (leagueName.equals("Spanish La Liga", true)) {
                    leagueId = "4335"
                }
                Log.d("league", leagueName)
                Log.d("league", leagueId)
                presenter.getNextEventsList(leagueId)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        //eventList
        eventList.layoutManager = LinearLayoutManager(activity)
        adapter = EventListAdapter(events) {
            activity?.startActivity<DetailEventActivity>("idEvent" to "${it.eventId}")
        }
        eventList.adapter = adapter

        swipeRefresh.onRefresh {
            presenter.getNextEventsList(leagueId)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //bind item
        val v: View = inflater.inflate(R.layout.fragment_next_event, container, false)
        spinner = v.findViewById(R.id.spinner_next_event)
        eventList = v.findViewById(R.id.next_event)
        progressBar = v.findViewById(R.id.progressBar_next_event)
        swipeRefresh = v.findViewById(R.id.swipeRefresh_next_event)

        return v
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setQueryHint("Find Event")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty() or query.isNullOrBlank()) {
                    presenter.getNextEventsList(leagueId)
                } else {
                    presenter.getSearchEventList(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty() or query.isNullOrBlank()) {
                    presenter.getNextEventsList(leagueId)
                } else {
                    presenter.getSearchEventList(query)
                }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}
