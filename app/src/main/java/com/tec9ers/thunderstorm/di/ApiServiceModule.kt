package com.tec9ers.thunderstorm.di

import com.tec9ers.thunderstorm.data.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

// TODO Install this is ActivityExtendedScope

@Module
@InstallIn(ApplicationComponent::class)
object ApiServiceModule {

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}