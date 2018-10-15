package com.rahmanarif.footballclub.view.fragment.schedule

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rahmanarif.footballclub.R
import com.rahmanarif.footballclub.adapter.TabPagerAdapter

class ScheduleFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    companion object {
        fun scheduleInstance(): ScheduleFragment = ScheduleFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_schedule, container, false)
        viewPager = v.findViewById(R.id.view_pager_scedule)
        tabLayout = v.findViewById(R.id.tab_schedule)

        setupViewPager(viewPager)
//        viewPager.adapter = fragmentManager?.let { VPAdapter(it) }
        tabLayout.setupWithViewPager(viewPager)
        return v
    }

    private fun setupViewPager(pager: ViewPager) {
        val adapter = fragmentManager?.let { TabPagerAdapter(it) }

        val next = NextEventFragment.nextIntance()
        adapter?.addFragment(next, "NEXT EVENT")

        val past = PastEventFragment.pastInstance()
        adapter?.addFragment(past, "PAST EVENT")

        pager?.adapter = adapter
    }
}
