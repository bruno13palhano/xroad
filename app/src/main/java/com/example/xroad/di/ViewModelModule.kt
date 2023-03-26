package com.example.xroad.di

import androidx.lifecycle.ViewModel
import com.example.xroad.viewmodel.RoadViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindRoadViewModel(impl: RoadViewModel): ViewModel
}