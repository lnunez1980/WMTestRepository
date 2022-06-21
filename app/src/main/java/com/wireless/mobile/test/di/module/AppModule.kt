package com.wireless.mobile.test.di.module

import androidx.lifecycle.ViewModelProvider
import com.wireless.mobile.test.factories.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}