package com.example.core.di

import com.example.core.repository.PathRepository
import com.example.core.repository.SimplePathRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class SimplePathRep

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RepositoryModule {

    @SimplePathRep
    @Singleton
    @Binds
    abstract fun bindSinglePathRepository(repository: SimplePathRepository): PathRepository
}