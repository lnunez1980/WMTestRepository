package com.wireless.mobile.test.ui.bottomsheet

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wireless.mobile.test.api.models.Country
import com.wireless.mobile.test.api.models.CountryInfo
import com.wireless.mobile.test.ui.repositories.SearchRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel(), DefaultLifecycleObserver {

    private val _countryActions = MutableLiveData<CountryActions>()
    val countryActions: LiveData<CountryActions> = _countryActions

    private var disposable: Disposable? = null

    override fun onDestroy(owner: LifecycleOwner) {
        disposable = null
        super.onDestroy(owner)
    }

    fun getCountryInfo(code: String, borders: Array<String>?) {
        if (_countryActions.value == null) {
            setLoader(true)
            val countryBorders = borders?.joinToString().orEmpty().replace(" ","")
            disposable = Observable.zip(
                repository.getCountryInfo(code),
                repository.getCountriesByCodes(countryBorders),
                BiFunction { country: Country, borders: List<Country> ->
                    CountryInfo(country, borders)
                }
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ countryInfo ->
                    setLoader(false)
                    _countryActions.value = CountryActions.CountryFound(countryInfo.country, countryInfo.borders)
                }, {
                    _countryActions.value = CountryActions.OnError(it)
                })
        }
    }

    private fun setLoader(loading: Boolean) {
        _countryActions.value = CountryActions.CountryLoading(loading)
    }
}

sealed class CountryActions {
    class CountryLoading(val loading: Boolean) : CountryActions()
    class CountryFound(val country: Country, val borders: List<Country>) : CountryActions()
    class OnError(val error: Throwable) : CountryActions()
}