package com.mudassirkhan.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mudassirkhan.data.local.dao.GithubTrendingApiDao
import com.mudassirkhan.data.local.model.GithubTrendingLocalEntity

@Database(entities = arrayOf(GithubTrendingLocalEntity::class),version = 1, exportSchema = false)
abstract class GithubTrendingApiDatabase : RoomDatabase(){

    abstract fun githubTrendingApiDao() : GithubTrendingApiDao

    companion object {
        fun newInstance(context: Context): GithubTrendingApiDatabase {
            return Room.databaseBuilder(context, GithubTrendingApiDatabase::class.java, "github_trend.db").build()
        }
    }
}