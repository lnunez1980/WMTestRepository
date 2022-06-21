package com.wireless.mobile.test.di.module

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.wireless.mobile.test.api.ServicesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class GlideModule {

    @Provides
    @Singleton
    fun provideGlideInstance(application: Application) : RequestManager {
        return Glide.with(application)
    }
}