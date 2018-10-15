package com.rahmanarif.footballclub.view.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.rahmanarif.footballclub.R
import com.rahmanarif.footballclub.view.fragment.favorite.FavoriteFragment
import com.rahmanarif.footballclub.view.fragment.schedule.ScheduleFragment
import com.rahmanarif.footballclub.view.fragment.team.TeamFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private lateinit var fragment: Fragment
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.inflateMenu(R.menu.bottom_navigation_menu);
        fragmentManager = supportFragmentManager;

        fragmentManager.beginTransaction().replace(R.id.main_container, ScheduleFragment()).commit()

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            val id = item.getItemId()
            when(id){
                R.id.schedule_menu -> fragment = ScheduleFragment()
                R.id.team_menu -> fragment = TeamFragment()
                R.id.favorite_menu -> fragment = FavoriteFragment()
            }
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, fragment)
            transaction.commit()
            true
        }
    }
}

