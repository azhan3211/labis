package com.mizani.labis.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mizani.labis.domain.model.entity.OrderEntity
import com.mizani.labis.domain.model.entity.ProductCategoryEntity
import com.mizani.labis.domain.model.entity.ProductEntity
import com.mizani.labis.domain.model.entity.StoreEntity

@Database(
    entities = [
        StoreEntity::class,
        ProductEntity::class,
        ProductCategoryEntity::class,
        OrderEntity::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(RoomConverter::class)
abstract class LabisDatabaseRoom : RoomDatabase() {

    abstract fun store(): StoreDao
    abstract fun product(): ProductDao
    abstract fun productCategory(): ProductCategoryDao
    abstract fun orders(): OrderDao
}