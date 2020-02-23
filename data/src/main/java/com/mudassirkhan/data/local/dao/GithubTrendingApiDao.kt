package com.mudassirkhan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mudassirkhan.data.local.model.GithubTrendingLocalEntity
import io.reactivex.Maybe

@Dao
interface GithubTrendingApiDao {

    @Query("SELECT * FROM trendingList")
    fun getGithubTrendingRepositories(): Maybe<List<GithubTrendingLocalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg events: GithubTrendingLocalEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: GithubTrendingLocalEntity)

    @Query("DELETE FROM trendingList")
    fun deleteAllRepositories()
}