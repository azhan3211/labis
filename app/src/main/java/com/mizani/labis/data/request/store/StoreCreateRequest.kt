package com.mizani.labis.data.request.store

data class StoreCreateRequest(
    val id: Int? = null,
    val name: String,
    val address: String
)