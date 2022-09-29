package com.aran.githubappusersub3.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aran.githubappusersub3.R
import com.aran.githubappusersub3.adapter.ListUsersAdapter
import com.aran.githubappusersub3.data.response.Users
import com.aran.githubappusersub3.databinding.ActivityMainBinding
import com.aran.githubappusersub3.viewmodel.AllUsersViewModel
import com.aran.githubappusersub3.viewmodel.SearchViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapterList: ListUsersAdapter
    private lateinit var allUserViewModel: AllUsersViewModel
    private lateinit var searchUserModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerViewList()
        showLoading(true)
        searchViewUser()
        showAllUsers()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.favorite_user -> {
                val intent = Intent(this, FavoriteUserActivity::class.java)
                startActivity(intent)
            }
            R.id.settings -> {
                val intentSetting = Intent(this, SettingsActivity::class.java)
                startActivity(intentSetting)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun recyclerViewList() {
        userAdapterList = ListUsersAdapter()

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = userAdapterList
        binding.rvUser.setHasFixedSize(true)

        userAdapterList.setOnItemClickCallback(object : ListUsersAdapter.OnItemClickedCallback {
            override fun onItemClicked(data: Users) {
                userDataSelected(data)
            }
        })
    }

    private fun userDataSelected(user: Users) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, user.login)
        this@MainActivity.startActivity(intent)
    }

    private fun showAllUsers() {
        allUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(AllUsersViewModel::class.java)
        allUserViewModel.setAllUsers(this)
        allUserViewModel.getAllUsers().observe(this) { listUsers ->
            if (listUsers != null) {
                userAdapterList.setData(listUsers)
                showLoading(false)
            }
        }
    }

    private fun showSearchDataUsers(username: String) {
        searchUserModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(SearchViewModel::class.java)
        searchUserModel.setSearchUsers(username, this)
        showLoading(true)
        searchUserModel.getSearchUsers().observe(this) { listSearchUsers ->
            if (listSearchUsers != null) {
                userAdapterList.setData(listSearchUsers)
                showLoading(false)
            }
        }
    }

    private fun searchViewUser() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return true
                } else {
                    showSearchDataUsers(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    showAllUsers()
                } else {
                    showLoading(false)
                    showSearchDataUsers(newText)
                    showLoading(true)
                }
                return true
            }
        })
    }
}