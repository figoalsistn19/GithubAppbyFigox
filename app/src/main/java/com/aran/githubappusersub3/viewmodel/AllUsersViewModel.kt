package com.aran.githubappusersub3.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aran.githubappusersub3.api.ApiConfig
import com.aran.githubappusersub3.data.response.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllUsersViewModel: ViewModel() {
    private var listUsers = MutableLiveData<ArrayList<Users>>()

    fun setAllUsers(context: Context) {
        ApiConfig.getService().getUsers().enqueue(object : Callback<ArrayList<Users>> {
            override fun onResponse(
                call: Call<ArrayList<Users>>,
                response: Response<ArrayList<Users>>
            ) {
                if (response.isSuccessful) {
                    listUsers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Toast.makeText(context, "onFailure: ${response.message()}", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<ArrayList<Users>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun getAllUsers(): LiveData<ArrayList<Users>> {
        return listUsers
    }

    companion object {
        const val TAG = "TAG"
    }
}