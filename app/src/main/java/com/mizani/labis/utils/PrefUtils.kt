package com.mizani.labis.utils

import android.content.Context
import android.content.SharedPreferences

class PrefUtils(
    private val context: Context
) {

    private fun getSetting(prefName: String): SharedPreferences {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    private fun getEditor(prefName: String): SharedPreferences.Editor {
        return getSetting(prefName).edit()
    }

    fun setString(prefName: String, key: String, value: String) {
        getEditor(prefName).putString(key, value).commit()
    }

    fun getString(prefName: String, key: String) : String {
        return getSetting(prefName).getString(key, null).orEmpty()
    }

    fun setInt(prefName: String, key: String, value: Int) {
        getEditor(prefName).putInt(key, value).commit()
    }

    fun getInt(prefName: String, key: String) : Int {
        return getSetting(prefName).getInt(key, 0)
    }

    fun setBoolean(prefName: String, key: String, value: Boolean) {
        getEditor(prefName).putBoolean(key, value).commit()
    }

    fun getBoolean(prefName: String, key: String) : Boolean {
        return getSetting(prefName).getBoolean(key, false)
    }

    fun destroy(prefName: String?) {
        getSetting(prefName.orEmpty()).edit().clear().commit()
    }

    fun setLong(prefName: String, key: String, value: Long) {
        getEditor(prefName).putLong(key, value).commit()
    }

    fun getLong(prefName: String, key: String) : Long {
        return getSetting(prefName).getLong(key, 0)
    }

}