package com.wireless.mobile.test.di.module

import androidx.lifecycle.ViewModel
import com.wireless.mobile.test.factories.ViewModelKey
import com.wireless.mobile.test.ui.bottomsheet.DetailBottomSheet
import com.wireless.mobile.test.ui.bottomsheet.DetailViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class DetailBottomSheetBuilder {

    @ContributesAndroidInjector
    abstract fun bindDetailBottomSheet(): DetailBottomSheet

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindViewModel(viewModel: DetailViewModel): ViewModel
}