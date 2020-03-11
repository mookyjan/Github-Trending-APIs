package com.mudassirkhan.data.util

import android.content.Context
import android.content.SharedPreferences
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class IPreference @Inject constructor(private val context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences("RepositoriesPref", Context.MODE_PRIVATE)
    var edit: SharedPreferences.Editor? = null

    init {
        edit = prefs!!.edit()
    }

    fun setLong(key: String, time: Long) {
        prefs.edit().putLong(key, time).apply()
    }

    fun getLong(key: String): Long {
        return prefs.getLong(key, 0)
    }
}