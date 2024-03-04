package com.mizani.labis.di

import com.mizani.labis.data.repository.PreferenceRepositoryImpl
import com.mizani.labis.data.repository.ProductRepositoryImpl
import com.mizani.labis.data.repository.StoreRepositoryImpl
import com.mizani.labis.domain.repository.PreferenceRepository
import com.mizani.labis.domain.repository.ProductRepository
import com.mizani.labis.domain.repository.StoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideStoreRepository(
        storeRepositoryImpl: StoreRepositoryImpl
    ): StoreRepository

    @Binds
    fun provideProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    fun providePreferenceRepository(
        preferenceRepositoryImpl: PreferenceRepositoryImpl
    ): PreferenceRepository


}