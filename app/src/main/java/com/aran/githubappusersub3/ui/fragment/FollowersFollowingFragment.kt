package com.aran.githubappusersub3.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aran.githubappusersub3.R
import com.aran.githubappusersub3.adapter.ListUsersAdapter
import com.aran.githubappusersub3.viewmodel.FollowersViewModel
import com.aran.githubappusersub3.viewmodel.FollowingViewModel

class FollowersFollowingFragment : Fragment() {
    private lateinit var adapterList: ListUsersAdapter
    private lateinit var getFollowersModel: FollowersViewModel
    private lateinit var getFollowingModel: FollowingViewModel

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val USERNAME = "section_username"

        @JvmStatic
        fun newInstance(index: Int, username: String) = FollowersFollowingFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_SECTION_NUMBER, index)
                putString(USERNAME, username)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterList = ListUsersAdapter()

        val tvUsers: RecyclerView = view.findViewById(R.id.rv_followers)
        tvUsers.layoutManager = LinearLayoutManager(this.context)
        tvUsers.adapter = adapterList

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(USERNAME)
        val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)

        if (index == 0) {
            showFollowers(username.toString(), progressBar)
        } else {
            showFollowing(username.toString(), progressBar)
        }
    }

    private fun showFollowers(username: String, progressbar: ProgressBar) {
        getFollowersModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)
        getFollowersModel.setData(username, requireContext())
        getFollowersModel.getData().observe(viewLifecycleOwner) { listUsers ->
            if (listUsers != null) {
                progressbar.visibility = View.GONE
                adapterList.setData(listUsers)
            } else {
                progressbar.visibility = View.GONE
            }
        }
    }

    private fun showFollowing(username: String, progressbar: ProgressBar) {
        getFollowingModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)
        getFollowingModel.setData(username, requireContext())
        getFollowingModel.getData().observe(viewLifecycleOwner) { listUsers ->
            if (listUsers != null) {
                progressbar.visibility = View.GONE
                adapterList.setData(listUsers)
            } else {
                progressbar.visibility = View.GONE
            }
        }
    }
}