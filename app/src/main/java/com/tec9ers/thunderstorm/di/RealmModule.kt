package com.tec9ers.thunderstorm.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.realm.Realm
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RealmModule {
    @Singleton
    @Provides
    fun provideRealmInstance():Realm{
        return Realm.getDefaultInstance()
    }
}