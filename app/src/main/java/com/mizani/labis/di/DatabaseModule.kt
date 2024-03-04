package com.mizani.labis.di

import android.content.Context
import androidx.room.Room
import com.mizani.labis.data.local.LabisDatabaseRoom
import com.mizani.labis.data.local.ProductCategoryDao
import com.mizani.labis.data.local.ProductDao
import com.mizani.labis.data.local.StoreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideStoreDao(labisDatabaseRoom: LabisDatabaseRoom): StoreDao {
        return labisDatabaseRoom.store()
    }

    @Singleton
    @Provides
    fun provideProductDao(labisDatabaseRoom: LabisDatabaseRoom): ProductDao {
        return labisDatabaseRoom.product()
    }

    @Singleton
    @Provides
    fun provideProductCategoryDao(labisDatabaseRoom: LabisDatabaseRoom): ProductCategoryDao {
        return labisDatabaseRoom.productCategory()
    }

    @Provides
    @Singleton
    fun provideLabisRoom(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        LabisDatabaseRoom::class.java,
        "mizani_labis_db"
    ).build()


}
