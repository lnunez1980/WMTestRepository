package com.wireless.mobile.test.di.module

import androidx.lifecycle.ViewModel
import com.wireless.mobile.test.api.ServicesApi
import com.wireless.mobile.test.factories.ViewModelKey
import com.wireless.mobile.test.ui.fragments.SearchFragment
import com.wireless.mobile.test.ui.fragments.SearchViewModel
import com.wireless.mobile.test.ui.repositories.SearchRepository
import com.wireless.mobile.test.ui.repositories.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class SearchFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun bindSearchFragment(): SearchFragment

    @Binds
    abstract fun bindRepository(impl: SearchRepositoryImpl): SearchRepository

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindViewModel(viewModel: SearchViewModel): ViewModel
}