package com.mizani.labis.domain.repository

interface PreferenceRepository {

    fun setSelectedStoreId(id: Long)

    fun getSelectedStoreId(): Long

    fun setToken(token: String)

    fun setRefreshToken(refreshToken: String)

    fun setLogin(isLogin: Boolean)

    fun getToken(): String

    fun getRefreshToken(): String

    fun deleteToken()

    fun isLogin(): Boolean

    fun removeAll()
}