package com.wireless.mobile.test.ui.fragments

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wireless.mobile.test.api.models.Country
import com.wireless.mobile.test.ui.repositories.SearchRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel(), DefaultLifecycleObserver {

    private val _countryActions = MutableLiveData<CountriesActions>()
    val countryActions: LiveData<CountriesActions> = _countryActions

    private val _searchInput = BehaviorSubject.create<String>()
    private val searchInput = _searchInput.toFlowable(BackpressureStrategy.LATEST)

    private var countries: List<Country> = emptyList()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var lastSearch: String = ""

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        if (_countryActions.value == null) {
            fetchCountries()
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        compositeDisposable.clear()
        this.countries = emptyList()
        super.onDestroy(owner)
    }

    fun filterCountries(search: String) {
        _searchInput.onNext(search)
        compositeDisposable.add(
            searchInput
                .debounce(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (lastSearch != it) {
                        lastSearch = it
                        getCountriesByName(it)
                    }
                }
        )
    }

    fun restoreData() {
        countries = emptyList()
        fetchCountries()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    fun getCountriesByName(names: String) {
        setLoader(true)
        compositeDisposable.add(repository.getCountriesByName(names)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ countries ->
                setLoader(false)
                _countryActions.value = CountriesActions.CountriesFound(countries, true)
            }, {
                setLoader(false)
                _countryActions.value = CountriesActions.OnError(it)
            })
        )
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    fun fetchCountries() {
        setLoader(true)
        countries = emptyList()
        compositeDisposable.add(repository.fetchCountries()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                if (countries.isEmpty().not()){
                    setLoader(false)
                    _countryActions.value = CountriesActions.CountriesFound(countries)
                }
            }
            .subscribe({ countries ->
                this.countries += countries
            }, {
                _countryActions.value = CountriesActions.OnError(it)
            })
        )
    }

    private fun setLoader(loading: Boolean) {
        _countryActions.value = CountriesActions.CountriesLoading(loading)
    }

}

sealed class CountriesActions {
    class CountriesLoading(val loading: Boolean) : CountriesActions()
    class CountriesFound(val countries: List<Country>, val isFiltering: Boolean = false) :
        CountriesActions()

    class OnError(val error: Throwable) : CountriesActions()
}