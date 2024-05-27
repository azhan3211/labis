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
            database.execSQL("""
            CREATE TABLE labis_order_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                store_id INTEGER NOT NULL,
                name TEXT NOT NULL,
                orders TEXT NOT NULL,
                status TEXT NOT NULL,
                date_time INTEGER NOT NULL
            )
        """)

            // Copy the data from the old table to the new table with conditional status
            database.execSQL("""
            INSERT INTO labis_order_new (id, store_id, name, orders, status, date_time)
            SELECT id, store_id, name, orders, 
                   CASE WHEN is_paylater = 1 THEN 'UNPAID' ELSE 'PAID' END AS status, 
                   date_time
            FROM labis_order
        """)

            // Remove the old table
            database.execSQL("DROP TABLE labis_order")

            // Rename the new table to the old table name
            database.execSQL("ALTER TABLE labis_order_new RENAME TO labis_order")
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
