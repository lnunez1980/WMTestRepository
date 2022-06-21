package com.wireless.mobile.test.di.module

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GlideModule {

    @Provides
    @Singleton
    fun provideGlideInstance(application: Application): RequestManager {
        return Glide.with(application)
    }
}