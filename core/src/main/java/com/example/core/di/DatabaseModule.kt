package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.database.PathDao
import com.example.core.database.XRoadDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object DatabaseModule {

    @Provides
    fun providePathDao(database: XRoadDatabase): PathDao {
        return database.pathDao
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): XRoadDatabase {
        return Room.databaseBuilder(
            appContext,
            XRoadDatabase::class.java,
            "road_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}