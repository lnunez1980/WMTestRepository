package com.wireless.mobile.test.ui.repositories

import com.wireless.mobile.test.api.ServicesApi
import com.wireless.mobile.test.api.models.Country
import io.reactivex.Observable
import javax.inject.Inject

private const val BUFFER_SIZE = 50

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: ServicesApi
) : SearchRepository {

    override fun fetchCountries(): Observable<List<Country>> {
        return searchApi.getCountries()
            .flatMap { countryList -> Observable.fromIterable(countryList) }
            .buffer(BUFFER_SIZE)

    }

    override fun getCountryInfo(code: String): Observable<Country> {
        return searchApi.getCountryInfo(code)
            .flatMap { countryList -> Observable.just(countryList) }
            .map { it.firstOrNull() }

    }

    override fun getCountriesByCodes(codes: String): Observable<List<Country>> {
        return searchApi.getCountriesByCodes(codes)
    }

    override fun getCountriesByName(name: String): Observable<List<Country>> {
        return searchApi.getCountriesByName(name)
    }
}