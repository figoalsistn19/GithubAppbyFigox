package com.aran.githubappusersub3.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aran.githubappusersub3.ui.fragment.FollowersFollowingFragment
import com.aran.githubappusersub3.ui.fragment.RepositoryFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = " "

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> FollowersFollowingFragment.newInstance(position, username)
            1 -> FollowersFollowingFragment.newInstance(position, username)
            2 -> RepositoryFragment.newInstance(position, username)
            else -> {
                RepositoryFragment.newInstance(position, username)
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}