package com.mudassirkhan.trendinggithubapis.local.test

import android.content.Context
import androidx.room.Room
import com.mudassirkhan.data.local.db.GithubTrendingApiDatabase

object TestDatabase {

    fun newSystemInstance(context: Context): GithubTrendingApiDatabase {
        return Room.inMemoryDatabaseBuilder(context, GithubTrendingApiDatabase::class.java).build()
    }

}