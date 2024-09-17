package com.mizani.labis.data.repository

import com.mizani.labis.domain.repository.PreferenceRepository
import com.mizani.labis.utils.PrefUtils
import javax.inject.Inject

class PreferenceRepositoryImpl @Inject constructor(
    private val prefUtils: PrefUtils
) : PreferenceRepository {

    override fun setSelectedStoreId(id: Long) {
        prefUtils.setLong(STORE_PREF, STORE_ID, id)
    }

    override fun getSelectedStoreId(): Long {
        return prefUtils.getLong(STORE_PREF, STORE_ID)
    }

    override fun setToken(token: String) {
        prefUtils.setString(AUTH_PREF, AUTH_TOKEN, token)
    }

    override fun setRefreshToken(refreshToken: String) {
        prefUtils.setString(AUTH_PREF, AUTH_REFRESH_TOKEN, refreshToken)
    }

    override fun setLogin(isLogin: Boolean) {
        prefUtils.setBoolean(AUTH_PREF, AUTH_IS_LOGIN, isLogin)
    }

    override fun getToken(): String {
        return prefUtils.getString(AUTH_PREF, AUTH_TOKEN)
    }

    override fun getRefreshToken(): String {
        return prefUtils.getString(AUTH_PREF, AUTH_REFRESH_TOKEN)
    }

    override fun deleteToken() {
        prefUtils.setString(AUTH_PREF, AUTH_TOKEN, "")
    }

    override fun isLogin(): Boolean {
        return prefUtils.getBoolean(AUTH_PREF, AUTH_IS_LOGIN)
    }

    override fun removeAll() {
        prefUtils.destroy(STORE_PREF)
        prefUtils.destroy(AUTH_PREF)
    }

    companion object {

        const val STORE_PREF = "STORE_PREF"
        const val STORE_ID = "STORE_ID"

        const val AUTH_PREF = "AUTH_PREF"
        const val AUTH_TOKEN = "AUTH_TOKEN"
        const val AUTH_REFRESH_TOKEN = "AUTH_REFRESH_TOKEN"
        const val AUTH_IS_LOGIN = "AUTH_IS_LOGIN"

    }
}