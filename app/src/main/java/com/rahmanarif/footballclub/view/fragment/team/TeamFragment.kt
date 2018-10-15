package com.rahmanarif.footballclub.view.fragment.team

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import com.google.gson.Gson
import com.rahmanarif.footballclub.R
import com.rahmanarif.footballclub.adapter.TeamListAdapter
import com.rahmanarif.footballclub.api.ApiRepository
import com.rahmanarif.footballclub.model.Team
import com.rahmanarif.footballclub.presenter.TeamPresenter
import com.rahmanarif.footballclub.util.invisible
import com.rahmanarif.footballclub.util.visible
import com.rahmanarif.footballclub.view.activity.detail_team.DetailTeamActivity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class TeamFragment : Fragment(), TeamView {
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamListAdapter
    private lateinit var spinner: Spinner
    private lateinit var teamList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueName: String

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }


    companion object {
        fun teamsInstance(): TeamFragment = TeamFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this, request, gson)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                leagueName = leagueName.replace(" ", "%20")
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        //eventList
        teamList.layoutManager = LinearLayoutManager(activity)
        adapter = TeamListAdapter(teams){
            activity?.startActivity<DetailTeamActivity>("idTeam" to "${it.teamId}")
        }
        teamList.adapter = adapter

        swipeRefresh.onRefresh {
            presenter.getTeamList(leagueName)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_team, container, false)
        spinner = v.findViewById(R.id.spinner_team)
        teamList = v.findViewById(R.id.team_list)
        progressBar = v.findViewById(R.id.progressBar_team)
        swipeRefresh = v.findViewById(R.id.swipeRefresh_team)

        return v
    }

}
