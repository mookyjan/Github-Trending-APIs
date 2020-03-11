package com.mudassirkhan.data.util

import android.content.Context
import android.content.SharedPreferences
import java.util.concurrent.TimeUnit
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

    /**
     * fetch data after two hours
     */
    fun isFetchNeeded():Boolean{
        val currentTime =System.currentTimeMillis()
        val lastUpdateTime = getLastTimeSaved()
        val hours = TimeUnit.MILLISECONDS.toHours( currentTime- lastUpdateTime!!)
        return hours>2
    }
}