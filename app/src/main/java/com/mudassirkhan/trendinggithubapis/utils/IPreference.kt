package com.mudassirkhan.githubtrendingapis.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class IPreference @Inject constructor(private val context: Context) {

    companion object {
        const val SAVED_AT_TIME = "saved_at_time"
    }

    private lateinit var prefs: SharedPreferences
    var edit: SharedPreferences.Editor? = null
    init {
        this.prefs = context.getSharedPreferences("RepositoriesPref", Context.MODE_PRIVATE)
        edit =prefs!!.edit()
    }


    fun setSavedTime(time: Long) {
        prefs.edit().putLong(SAVED_AT_TIME, time).apply()
    }

    fun getLastTimeSaved(): Long? {
        return prefs.getLong(SAVED_AT_TIME, 0)
    }
}