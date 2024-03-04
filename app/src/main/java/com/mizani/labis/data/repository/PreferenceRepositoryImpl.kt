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

    companion object {

        const val STORE_PREF = "STORE_PREF"
        const val STORE_ID = "STORE_ID"

    }
}