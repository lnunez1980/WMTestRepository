package com.wireless.mobile.test.di.module

import com.wireless.mobile.test.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityBuilder {

    @ContributesAndroidInjector(
        modules = [
            SearchFragmentBuilder::class,
            DetailBottomSheetBuilder::class
        ]
    )

    abstract fun bindMainActivity(): MainActivity

}