package com.aran.githubappusersub3.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aran.githubappusersub3.adapter.FavoritesAdapter
import com.aran.githubappusersub3.data.local.DataRoom
import com.aran.githubappusersub3.databinding.ActivityFavoriteBinding
import com.aran.githubappusersub3.viewmodel.FavoriteViewModel

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.title = "Favorite User"
        actionbar?.setDisplayHomeAsUpEnabled(true)

        setLoading(true)
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
    }

    private fun setLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setData(list: ArrayList<DataRoom>) {
        favoriteAdapter = FavoritesAdapter(list, this)
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite .adapter = favoriteAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getDataFavorite(this).observe(this) { listUsers ->
            if (listUsers != null) {
                setData(listUsers)
                setLoading(false)
            }
        }
    }
}