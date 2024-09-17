package com.mizani.labis.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mizani.labis.data.local.LabisDatabaseRoom
import com.mizani.labis.data.local.OrderDao
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

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Empty implementation, because the schema isn't changing.
        }
    }

    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {

        }
    }

    private val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            
        }
    }

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

    @Singleton
    @Provides
    fun provideOrderDao(labisDatabaseRoom: LabisDatabaseRoom): OrderDao {
        return labisDatabaseRoom.orders()
    }

    @Provides
    @Singleton
    fun provideLabisRoom(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        LabisDatabaseRoom::class.java,
        "mizani_labis_db"
    )
        .addMigrations(
            MIGRATION_1_2,
            MIGRATION_2_3,
            MIGRATION_3_4
        )
        .build()


}
