package com.aran.githubappusersub3.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataDao {
    @Insert
    fun insert(user: DataRoom)

    @Query("DELETE from data_user where username= :username")
    fun delete(username: String)

    @Query("SELECT * from data_user order by id asc")
    fun getAllData(): List<DataRoom>

    @Query("SELECT EXISTS(SELECT * from data_user where username= :username)")
    fun dataExist(username: String): Boolean
}