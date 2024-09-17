package com.mizani.labis.di

import android.content.Context
import android.content.Intent
import com.mizani.labis.data.remote.order.OrderService
import com.mizani.labis.data.remote.auth.AuthService
import com.mizani.labis.data.remote.expenditure.CapitalExpenditureService
import com.mizani.labis.data.remote.product.ProductService
import com.mizani.labis.data.remote.store.StoreService
import com.mizani.labis.domain.repository.PreferenceRepository
import com.mizani.labis.ui.screen.auth.AuthActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WebServiceModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        @ApplicationContext context: Context,
        preferenceRepository: PreferenceRepository
    ): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${preferenceRepository.getToken()}")
                .build()

            val response = chain.proceed(request)

            if (response.code() == 401 || response.code() == 403) {
                // Redirect to login activity
                if (preferenceRepository.isLogin()) {
                    val intent = Intent(context, AuthActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    preferenceRepository.removeAll()
                    context.startActivity(intent)
                }
            }

            response
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .readTimeout(3, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://10.0.2.2:8000") // Replace with your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideStoreService(retrofit: Retrofit): StoreService {
        return retrofit.create(StoreService::class.java)
    }

    @Singleton
    @Provides
    fun provideProductService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }

    @Singleton
    @Provides
    fun provideOrderService(retrofit: Retrofit): OrderService {
        return retrofit.create(OrderService::class.java)
    }

    @Singleton
    @Provides
    fun provideCapitalExpenditureService(retrofit: Retrofit): CapitalExpenditureService {
        return retrofit.create(CapitalExpenditureService::class.java)
    }

}