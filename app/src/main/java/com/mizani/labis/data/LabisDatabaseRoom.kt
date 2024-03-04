package com.mizani.labis.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mizani.labis.data.dao.ProductCategoryDao
import com.mizani.labis.data.dao.ProductDao
import com.mizani.labis.data.dao.StoreDao
import com.mizani.labis.data.entity.ProductCategoryEntity
import com.mizani.labis.data.entity.ProductEntity
import com.mizani.labis.data.entity.StoreEntity

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