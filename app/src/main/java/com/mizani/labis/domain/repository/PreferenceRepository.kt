package com.mizani.labis.domain.repository

interface PreferenceRepository {

    fun setSelectedStoreId(id: Long)
    fun getSelectedStoreId(): Long
}