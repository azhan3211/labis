package com.mizani.labis.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mizani.labis.domain.model.entity.OrdersEntity
import java.util.Date

class RoomConverter {

    @TypeConverter
    fun ordersToString(orders: List<OrdersEntity>) = Gson().toJson(orders)

    @TypeConverter
    fun stringToOrder(orders: String) = Gson().fromJson<List<OrdersEntity>>(orders, object : TypeToken<List<OrdersEntity>>(){}.type)

    @TypeConverter
    fun longToDate(dateLong: Long): Date {
        return Date(dateLong)
    }

    @TypeConverter
    fun dateToLong(date: Date): Long {
        return date.time
    }
}