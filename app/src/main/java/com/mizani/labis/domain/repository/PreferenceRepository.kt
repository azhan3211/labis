package com.mizani.labis.domain.repository

import com.mizani.labis.data.dto.store.StoreDto

interface PreferenceRepository {

    fun setSelectedStoreId(id: Long)
    fun getSelectedStoreId(): Long
}