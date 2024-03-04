package com.mizani.labis.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mizani.labis.data.local.ProductCategoryDao
import com.mizani.labis.data.local.ProductDao
import com.mizani.labis.data.local.StoreDao
import com.mizani.labis.domain.model.entity.ProductCategoryEntity
import com.mizani.labis.domain.model.entity.ProductEntity
import com.mizani.labis.domain.model.entity.StoreEntity

@Database(
    entities = [
        StoreEntity::class,
        ProductEntity::class,
        ProductCategoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
//@TypeConverters(DataConverters::class)
abstract class LabisDatabaseRoom : RoomDatabase() {

    abstract fun store(): StoreDao
    abstract fun product(): ProductDao
    abstract fun productCategory(): ProductCategoryDao
}