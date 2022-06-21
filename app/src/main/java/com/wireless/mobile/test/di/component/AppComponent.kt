package com.wireless.mobile.test.di.component

import android.app.Application
import com.wireless.mobile.test.BaseApplication
import com.wireless.mobile.test.di.module.ApiModule
import com.wireless.mobile.test.di.module.AppModule
import com.wireless.mobile.test.di.module.GlideModule
import com.wireless.mobile.test.di.module.MainActivityBuilder
import com.wireless.mobile.test.di.module.RetrofitModule
import com.wireless.mobile.test.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        GlideModule::class,
        AppModule::class,
        ApiModule::class,
        RetrofitModule::class,
        MainActivityBuilder::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}