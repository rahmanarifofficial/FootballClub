package com.rahmanarif.footballclub.view.fragment.favorite

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rahmanarif.footballclub.R
import com.rahmanarif.footballclub.adapter.TabPagerAdapter

class FavoriteFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    companion object {
        fun favoritesInstance(): FavoriteFragment = FavoriteFragment()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_favorite, container, false)
        viewPager = v.findViewById(R.id.view_pager_favorite)
        tabLayout = v.findViewById(R.id.tab_favorite)

        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)

        return v
    }

    private fun setupViewPager(pager: ViewPager) {
        val adapter = fragmentManager?.let { TabPagerAdapter(it) }

        val match = FavoriteMatchFragment.matchIntance()
        adapter?.addFragment(match, "MATCHES")

        val team = FavoriteTeamFragment.teamIntance()
        adapter?.addFragment(team, "TEAMS")

        pager?.adapter = adapter
    }

}
