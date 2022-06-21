package com.wireless.mobile.test.di.module

import com.wireless.mobile.test.api.ServicesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideCountriesApi(retrofit: Retrofit) : ServicesApi {
        return retrofit.create(ServicesApi::class.java)
    }
}