package com.mizani.labis.di

import android.content.Context
import com.mizani.labis.utils.PrefUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Singleton
    @Provides
    fun providePrefUtils(@ApplicationContext context: Context): PrefUtils {
        return PrefUtils(context)
    }

}