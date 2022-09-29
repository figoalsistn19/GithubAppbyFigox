package com.aran.githubappusersub3.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.aran.githubappusersub3.R
import com.aran.githubappusersub3.adapter.SectionPagerAdapter
import com.aran.githubappusersub3.data.response.Users
import com.aran.githubappusersub3.data.local.DataDao
import com.aran.githubappusersub3.data.local.DataRoom
import com.aran.githubappusersub3.data.local.DatabaseRoom
import com.aran.githubappusersub3.databinding.ActivityDetailUserBinding
import com.aran.githubappusersub3.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var getUserModel: DetailViewModel
    private lateinit var database: DataDao

    private var url = ""
    private var userName = ""
    private var avatar = ""

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra(EXTRA_USER).toString()
        val actionbar = supportActionBar
        actionbar?.title = userName
        actionbar?.setDisplayHomeAsUpEnabled(true)

        database = DatabaseRoom.getDatabase(applicationContext).dataDao()
        val exist = database.dataExist(userName)
        setIcon(exist)

        showLoading(true)
        showDataUser(userName)

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager = binding.viewpager
        viewPager.adapter = sectionsPagerAdapter
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        sectionsPagerAdapter.username = userName

        binding.favorite.setOnClickListener(this)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                ivImg.visibility = View.GONE
                tvUsername.visibility = View.GONE
                tvName.visibility = View.GONE
                tvLoc.visibility = View.GONE
                tvFollowers.visibility = View.GONE
                tvFollowing.visibility = View.GONE
                viewpager.visibility = View.GONE
                favorite.visibility = View.GONE
                tabs.visibility = View.GONE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.GONE
                ivImg.visibility = View.VISIBLE
                tvUsername.visibility = View.VISIBLE
                tvName.visibility = View.VISIBLE
                tvLoc.visibility = View.VISIBLE
                tvFollowers.visibility = View.VISIBLE
                tvFollowing.visibility = View.VISIBLE
                viewpager.visibility = View.VISIBLE
                favorite.visibility = View.VISIBLE
                tabs.visibility = View.VISIBLE
            }
        }
    }

    private fun showDataUser(username: String) {
        getUserModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        getUserModel.setUserDetail(username, this)
        getUserModel.getUserDetail().observe(this) { userData ->
            if (userData != null) {
                Log.d("tag", userData.toString())
                view(userData)
                showLoading(false)
            }
        }
    }

    private fun view(user: Users) {
        url = user.user_url.toString()
        avatar = user.avatar_url
        Glide.with(this)
            .load(user.avatar_url)
            .apply(RequestOptions().override(150, 150))
            .into(binding.ivImg)
        binding.tvName.text = user.name
        binding.tvUsername.text = user.login
        binding.tvLoc.text = user.location
        binding.tvFollowing.text = user.following.toString()
        binding.tvFollowers.text = user.followers.toString()
    }

    override fun onClick(v: View) {
        when (v) {
            binding.favorite -> {
                addFavorite(v)
            }
        }
    }

    private fun addFavorite(v: View) {
        val exist = database.dataExist(userName)
        setIcon(exist, v)
    }

    private fun setIcon(exist: Boolean) {
        if (exist) {
            binding.favorite.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_favorite
                )
            )
        } else {
            binding.favorite.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_favorite_white
                )
            )
        }
    }

    private fun setIcon(exist: Boolean, v: View) {
        if (!exist) {
            setIcon(true)
            Snackbar.make(v, "$userName ${getString(R.string.succes_add)}", Snackbar.LENGTH_SHORT)
                .show()
            val newData = DataRoom(
                username = userName,
                avatar = avatar,
            )
            database.insert(newData)
        } else {
            setIcon(false)
            Snackbar.make(
                v,
                "$userName ${getString(R.string.success_delete)}",
                Snackbar.LENGTH_SHORT
            ).show()
            database.delete(userName)
        }
    }

    companion object {
        const val EXTRA_USER = "extra_data"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2,
            R.string.tab_3
        )
    }
}